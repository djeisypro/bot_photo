import logging
from telegram import Update
from telegram.ext import ContextTypes
from PIL import Image
import os

from constants import *
from keyboards import *
from effects import apply_effect
from utils import cleanup_temp_files, get_output_path

async def start(update: Update, context: ContextTypes.DEFAULT_TYPE):
    """Обработчик команды /start"""
    # Очищаем данные пользователя
    context.user_data.clear()
    # Отправляем приветственное сообщение с клавиатурой
    await update.message.reply_text(
        "Привет! Я бот для обработки фотографий. Выберите режим:",
        reply_markup=get_start_keyboard()
    )

async def mode_select(update: Update, context: ContextTypes.DEFAULT_TYPE):
    """Обработчик выбора режима работы"""
    query = update.callback_query
    await query.answer()
    
    if query.data == SINGLE_PHOTO:
        # Устанавливаем режим единичной фотографии
        context.user_data['mode'] = 'single'
        await query.message.reply_text("Выберите действие:", reply_markup=get_single_photo_keyboard())
    elif query.data == GROUP_PHOTOS:
        # Устанавливаем режим групповой обработки
        context.user_data['mode'] = 'group'
        context.user_data['photo_paths'] = []
        await query.message.reply_text("Отправьте фотографии для групповой обработки.")
    elif query.data == STOP:
        await stop(update, context)

async def handle_photo(update: Update, context: ContextTypes.DEFAULT_TYPE):
    """Обработчик получения фотографии"""
    mode = context.user_data.get('mode')
    
    if not mode:
        await update.message.reply_text("Сначала выберите режим: /start")
        return

    # Получаем файл фотографии
    photo = await update.message.photo[-1].get_file()
    # Создаем путь для сохранения
    photo_path = f"temp_{update.effective_user.id}_{len(context.user_data.get('photo_paths', []))}.jpg"
    # Скачиваем фотографию
    await photo.download_to_drive(photo_path)
    
    # Проверяем, было ли ожидание фото для конкретного действия
    action_after_photo = context.user_data.pop('action_after_photo', None)

    if action_after_photo == 'process_single':
        # Если ожидали фото для обработки
        context.user_data['last_result'] = photo_path
        await process(update, context)
        return
    elif action_after_photo == 'crop_single':
        # Если ожидали фото для обрезки
        context.user_data['last_result'] = photo_path
        await crop_select(update, context)
        return
    elif mode == 'single':
        # Если это первое фото в режиме 'single'
        context.user_data['last_result'] = photo_path
        await update.message.reply_text(
            "Фото получено. Что хотите сделать дальше?", 
            reply_markup=get_post_process_keyboard()
        )
        return
    elif mode == 'group':
        # Если это фото в режиме 'group'
        if 'photo_paths' not in context.user_data:
            context.user_data['photo_paths'] = []
        context.user_data['photo_paths'].append(photo_path)
        if len(context.user_data['photo_paths']) == 1:
            await update.message.reply_text("Фото получено. Напишите /process для обработки.")
        else:
            await update.message.reply_text("Фото добавлено в группу.")

async def process(update: Update, context: ContextTypes.DEFAULT_TYPE):
    """Обработчик команды /process"""
    query = update.callback_query
    if query:
        await query.answer()
    
    # Получаем путь к последнему обработанному/обрезанному фото
    last_result = context.user_data.get('last_result')
    if last_result:
        photo_paths = [last_result]
    else:
        photo_paths = context.user_data.get('photo_paths', [])
    
    if not photo_paths:
        # Если нет фото, но пользователь нажал кнопку "Обработка" в начале
        if query and query.data == PROCESS_SINGLE_ASK_PHOTO:
            context.user_data['action_after_photo'] = 'process_single'
            await query.message.reply_text("Отправьте фото для обработки.")
            return
        elif query:
            await query.message.reply_text("Нет фотографий для обработки. Сначала отправьте фото.")
            return
        else:
            await update.message.reply_text("Нет фотографий для обработки. Сначала отправьте фото.")
            return
    
    # Получаем текущую страницу эффектов
    current_page = context.user_data.get('current_page', 1)
    
    # Отправляем клавиатуру с эффектами
    if query:
        await query.message.edit_text("Выберите тип обработки:", reply_markup=get_effects_keyboard(current_page))
    else:
        await update.message.reply_text("Выберите тип обработки:", reply_markup=get_effects_keyboard(current_page))

async def handle_page_navigation(update: Update, context: ContextTypes.DEFAULT_TYPE):
    """Обработчик навигации по страницам эффектов"""
    query = update.callback_query
    await query.answer()
    
    current_page = context.user_data.get('current_page', 1)
    
    if query.data == NEXT_PAGE:
        current_page += 1
    elif query.data == PREV_PAGE:
        current_page -= 1
    
    context.user_data['current_page'] = current_page
    await process(update, context)

async def process_photos(update: Update, context: ContextTypes.DEFAULT_TYPE):
    """Обработчик применения эффекта к фотографиям"""
    query = update.callback_query
    await query.answer()
    
    last_result = context.user_data.get('last_result')
    photo_paths = context.user_data.get('photo_paths', [])
    
    if not photo_paths and not last_result:
        await query.message.reply_text("Ошибка: фотографии не найдены. Пожалуйста, отправьте фотографии перед выбором обработки.")
        return
    
    # Определяем фотографии для обработки
    photos_to_process = [last_result] if last_result else photo_paths
    
    # Сохраняем список обработанных фотографий
    processed_photos = []
    
    for photo_path in photos_to_process:
        try:
            # Открываем и обрабатываем изображение
            image = Image.open(photo_path)
            image = apply_effect(image, query.data)
            
            # Создаем путь для сохранения
            output_path = get_output_path(photo_path, "processed")
            if image.mode == 'RGBA':
                output_path = output_path.replace('.jpg', '.png')
            
            # Сохраняем обработанное изображение
            image.save(output_path)
            await query.message.reply_photo(photo=open(output_path, 'rb'), caption="Обработанное изображение")
            
            processed_photos.append(output_path)
            
            # Удаляем исходное изображение
            try:
                os.remove(photo_path)
            except Exception as e:
                logging.error(f"Ошибка при удалении файла {photo_path}: {e}")
        except Exception as e:
            await query.message.reply_text(f"Произошла ошибка при обработке: {str(e)}")
            return
    
    # Сохраняем список обработанных фотографий
    context.user_data['photo_paths'] = processed_photos
    context.user_data['last_result'] = processed_photos[-1] if processed_photos else None
    
    # Отправляем клавиатуру с дальнейшими действиями
    await query.message.reply_text("Что хотите сделать дальше?", reply_markup=get_post_process_keyboard())

async def process_last_result(update: Update, context: ContextTypes.DEFAULT_TYPE):
    """Обработчик повторной обработки последнего результата"""
    query = update.callback_query
    await query.answer()
    
    # Проверяем, есть ли список фотографий для обработки
    photo_paths = context.user_data.get('photo_paths', [])
    if photo_paths:
        # Если есть список фотографий, обрабатываем их все
        context.user_data['last_result'] = None
        await process_photos(update, context)
        return
        
    # Если нет списка фотографий, обрабатываем только last_result
    last_result = context.user_data.get('last_result')
    if not last_result:
        await query.message.reply_text("Нет изображения для обработки. Начните заново.")
        return
        
    try:
        # Открываем и обрабатываем изображение
        image = Image.open(last_result)
        image = apply_effect(image, query.data)
        
        # Создаем путь для сохранения
        output_path = get_output_path(last_result, "processed", update.effective_user.id)
        if image.mode == 'RGBA':
            output_path = output_path.replace('.jpg', '.png')
            
        # Сохраняем обработанное изображение
        image.save(output_path)
        await query.message.reply_photo(photo=open(output_path, 'rb'), caption="Обработанное изображение")
        
        # Сохраняем путь к новому файлу
        context.user_data['last_result'] = output_path
        
        # Удаляем старый файл
        try:
            if last_result != output_path:
                os.remove(last_result)
        except Exception as e:
            logging.error(f"Ошибка при удалении старого файла: {e}")
            
        # Отправляем клавиатуру с дальнейшими действиями
        await query.message.reply_text("Что хотите сделать дальше?", reply_markup=get_post_process_keyboard())
    except Exception as e:
        await query.message.reply_text(f"Произошла ошибка при обработке: {str(e)}")
        logging.error(f"Ошибка при обработке изображения: {e}")

async def crop_select(update: Update, context: ContextTypes.DEFAULT_TYPE):
    """Обработчик выбора формата обрезки"""
    query = update.callback_query
    await query.answer()
    await query.message.reply_text("Выберите формат обрезки:", reply_markup=get_crop_keyboard())

async def crop_set_mode(update: Update, context: ContextTypes.DEFAULT_TYPE):
    """Обработчик установки режима обрезки"""
    query = update.callback_query
    await query.answer()
    
    # Устанавливаем режим обрезки
    if query.data == CROP_SQUARE:
        context.user_data['crop_mode'] = (1, 1)
    elif query.data == CROP_PORTRAIT:
        context.user_data['crop_mode'] = (9, 16)
    elif query.data == CROP_LANDSCAPE:
        context.user_data['crop_mode'] = (16, 9)
    
    # Получаем последний результат или загруженное фото
    last_result = context.user_data.get('last_result')
    last_uploaded_photo_for_action = context.user_data.pop('last_uploaded_photo_for_action', None)

    if last_uploaded_photo_for_action:
        context.user_data['photo_paths'] = [last_uploaded_photo_for_action]
        await crop_group_photos(update, context)
    elif last_result:
        context.user_data['photo_paths'] = [last_result]
        await crop_group_photos(update, context)
    else:
        await query.message.reply_text("Теперь отправьте фото, и я сразу пришлю его в выбранном формате.")

async def crop_group_photos(update: Update, context: ContextTypes.DEFAULT_TYPE):
    """Обработчик обрезки фотографий"""
    query = update.callback_query
    if query:
        await query.answer()
        
    crop_mode = context.user_data.get('crop_mode')
    
    # Определяем фотографии для обрезки
    photos_to_crop = []
    if context.user_data.get('last_result'):
        photos_to_crop = [context.user_data.pop('last_result')]
    elif context.user_data.get('photo_paths'):
        photos_to_crop = context.user_data['photo_paths']
    elif context.user_data.get('last_uploaded_photo_for_action'):
        photos_to_crop = [context.user_data.pop('last_uploaded_photo_for_action')]

    if not crop_mode:
        await (query.message if query else update.message).reply_text("Сначала выберите формат обрезки.")
        return
    if not photos_to_crop:
        await (query.message if query else update.message).reply_text("Нет фотографий для обрезки. Сначала отправьте фотографии.")
        return
    
    # Получаем соотношение сторон для обрезки
    aspect_w, aspect_h = crop_mode
    
    # Обрабатываем каждую фотографию
    for photo_path in photos_to_crop:
        try:
            # Открываем изображение
            image = Image.open(photo_path)
            img_w, img_h = image.size
            
            # Вычисляем размеры для обрезки
            target_ratio = aspect_w / aspect_h
            img_ratio = img_w / img_h
            
            if img_ratio > target_ratio:
                # Если изображение шире нужного соотношения
                new_w = int(img_h * target_ratio)
                left = (img_w - new_w) // 2
                box = (left, 0, left + new_w, img_h)
            else:
                # Если изображение выше нужного соотношения
                new_h = int(img_w / target_ratio)
                top = (img_h - new_h) // 2
                box = (0, top, img_w, top + new_h)
            
            # Обрезаем изображение
            cropped = image.crop(box)
            
            # Создаем путь для сохранения
            output_path = get_output_path(photo_path, "cropped")
            if image.mode == 'RGBA':
                output_path = output_path.replace('.jpg', '.png')
            
            # Сохраняем обрезанное изображение
            cropped.save(output_path)
            await (query.message if query else update.message).reply_photo(
                photo=open(output_path, 'rb'),
                caption="Обрезанное изображение"
            )
            
            # Сохраняем путь к обрезанному изображению
            context.user_data['last_result'] = output_path
            
            # Удаляем исходное изображение
            try:
                os.remove(photo_path)
            except Exception as e:
                logging.error(f"Ошибка при удалении файла {photo_path}: {e}")
        except Exception as e:
            await (query.message if query else update.message).reply_text(f"Произошла ошибка при обрезке: {str(e)}")
            return
    
    await (query.message if query else update.message).reply_text("Все фотографии обрезаны!")
    
    # Отправляем клавиатуру с дальнейшими действиями
    await (query.message if query else update.message).reply_text(
        "Что хотите сделать дальше?",
        reply_markup=get_post_crop_keyboard()
    )

async def stop(update: Update, context: ContextTypes.DEFAULT_TYPE):
    """Обработчик команды остановки"""
    query = update.callback_query
    await query.answer()
    await query.message.reply_text("Процесс остановлен.")

async def restart(update: Update, context: ContextTypes.DEFAULT_TYPE):
    """Обработчик перезапуска бота"""
    query = update.callback_query
    if query:
        await query.answer()
    
    # Очищаем временные файлы
    await cleanup_temp_files(context)
    
    # Очищаем данные пользователя
    context.user_data.clear()
    
    # Отправляем приветственное сообщение
    if query:
        await query.message.reply_text(
            "Привет! Я бот для обработки фотографий. Выберите режим:",
            reply_markup=get_start_keyboard()
        )
    else:
        await update.message.reply_text(
            "Привет! Я бот для обработки фотографий. Выберите режим:",
            reply_markup=get_start_keyboard()
        ) 