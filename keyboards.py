from telegram import InlineKeyboardButton, InlineKeyboardMarkup

from constants import (
    SINGLE_PHOTO, GROUP_PHOTOS, STOP, PROCESS_SINGLE_ASK_PHOTO,
    CROP_SINGLE_ASK_PHOTO, CROP_SQUARE, CROP_PORTRAIT, CROP_LANDSCAPE,
    ENHANCE, INVERT, BW, CARTOON, VIGNETTE, PENCIL, SOLARIZE, PIXELATE,
    ENHANCE_LAST, INVERT_LAST, BW_LAST, CARTOON_LAST, VIGNETTE_LAST,
    PENCIL_LAST, SOLARIZE_LAST, PIXELATE_LAST, NEXT_PAGE, PREV_PAGE,
    NEXT_PAGE_LAST, PREV_PAGE_LAST, POSTPROCESS_CROP, POSTCROP_CROP,
    POSTCROP_PROCESS, POSTPROCESS_PROCESS, RESTART
)

def get_start_keyboard() -> InlineKeyboardMarkup:
    """Создает клавиатуру для стартового меню"""
    keyboard = [
        [
            InlineKeyboardButton("Одна фотография", callback_data=SINGLE_PHOTO),
            InlineKeyboardButton("Группа фотографий", callback_data=GROUP_PHOTOS)
        ],
        [InlineKeyboardButton("Остановить", callback_data=STOP)]
    ]
    return InlineKeyboardMarkup(keyboard)

def get_single_photo_keyboard() -> InlineKeyboardMarkup:
    """Создает клавиатуру для режима одной фотографии"""
    keyboard = [
        [
            InlineKeyboardButton("Обработка", callback_data=PROCESS_SINGLE_ASK_PHOTO),
            InlineKeyboardButton("Обрезка", callback_data=CROP_SINGLE_ASK_PHOTO)
        ],
        [InlineKeyboardButton("Остановить", callback_data=STOP)]
    ]
    return InlineKeyboardMarkup(keyboard)

def get_crop_keyboard() -> InlineKeyboardMarkup:
    """Создает клавиатуру для выбора формата обрезки"""
    keyboard = [
        [
            InlineKeyboardButton("Квадрат", callback_data=CROP_SQUARE),
            InlineKeyboardButton("Портрет", callback_data=CROP_PORTRAIT)
        ],
        [
            InlineKeyboardButton("Пейзаж", callback_data=CROP_LANDSCAPE),
            InlineKeyboardButton("Остановить", callback_data=STOP)
        ]
    ]
    return InlineKeyboardMarkup(keyboard)

def get_effects_keyboard(page: int = 1) -> InlineKeyboardMarkup:
    """Создает клавиатуру с эффектами обработки"""
    # Определяем эффекты для текущей страницы
    effects = [
        (ENHANCE, "Улучшение"),
        (INVERT, "Инверсия"),
        (BW, "Ч/Б"),
        (CARTOON, "Мультфильм"),
        (VIGNETTE, "Виньетка"),
        (PENCIL, "Карандаш"),
        (SOLARIZE, "Соляризация"),
        (PIXELATE, "Пикселизация")
    ]
    
    # Разбиваем эффекты на страницы по 4 эффекта
    effects_per_page = 4
    start_idx = (page - 1) * effects_per_page
    end_idx = start_idx + effects_per_page
    current_effects = effects[start_idx:end_idx]
    
    # Создаем кнопки для эффектов
    keyboard = []
    for effect_id, effect_name in current_effects:
        keyboard.append([InlineKeyboardButton(effect_name, callback_data=effect_id)])
    
    # Добавляем кнопки навигации
    nav_buttons = []
    if page > 1:
        nav_buttons.append(InlineKeyboardButton("◀️", callback_data=PREV_PAGE))
    if end_idx < len(effects):
        nav_buttons.append(InlineKeyboardButton("▶️", callback_data=NEXT_PAGE))
    if nav_buttons:
        keyboard.append(nav_buttons)
    
    # Добавляем кнопку остановки
    keyboard.append([InlineKeyboardButton("Остановить", callback_data=STOP)])
    
    return InlineKeyboardMarkup(keyboard)

def get_effects_keyboard_last(page: int = 1) -> InlineKeyboardMarkup:
    """Создает клавиатуру с эффектами для повторной обработки"""
    # Определяем эффекты для текущей страницы
    effects = [
        (ENHANCE_LAST, "Улучшение"),
        (INVERT_LAST, "Инверсия"),
        (BW_LAST, "Ч/Б"),
        (CARTOON_LAST, "Мультфильм"),
        (VIGNETTE_LAST, "Виньетка"),
        (PENCIL_LAST, "Карандаш"),
        (SOLARIZE_LAST, "Соляризация"),
        (PIXELATE_LAST, "Пикселизация")
    ]
    
    # Разбиваем эффекты на страницы по 4 эффекта
    effects_per_page = 4
    start_idx = (page - 1) * effects_per_page
    end_idx = start_idx + effects_per_page
    current_effects = effects[start_idx:end_idx]
    
    # Создаем кнопки для эффектов
    keyboard = []
    for effect_id, effect_name in current_effects:
        keyboard.append([InlineKeyboardButton(effect_name, callback_data=effect_id)])
    
    # Добавляем кнопки навигации
    nav_buttons = []
    if page > 1:
        nav_buttons.append(InlineKeyboardButton("◀️", callback_data=PREV_PAGE_LAST))
    if end_idx < len(effects):
        nav_buttons.append(InlineKeyboardButton("▶️", callback_data=NEXT_PAGE_LAST))
    if nav_buttons:
        keyboard.append(nav_buttons)
    
    # Добавляем кнопки действий
    keyboard.append([
        InlineKeyboardButton("Обрезка", callback_data=POSTPROCESS_CROP),
        InlineKeyboardButton("Начать заново", callback_data=RESTART)
    ])
    
    return InlineKeyboardMarkup(keyboard)

def get_post_process_keyboard() -> InlineKeyboardMarkup:
    """Создает клавиатуру для действий после обработки"""
    keyboard = [
        [
            InlineKeyboardButton("Обрезка", callback_data=POSTPROCESS_CROP),
            InlineKeyboardButton("Начать заново", callback_data=RESTART)
        ]
    ]
    return InlineKeyboardMarkup(keyboard)

def get_post_crop_keyboard() -> InlineKeyboardMarkup:
    """Создает клавиатуру для действий после обрезки"""
    keyboard = [
        [
            InlineKeyboardButton("Обработка", callback_data=POSTCROP_PROCESS),
            InlineKeyboardButton("Начать заново", callback_data=RESTART)
        ]
    ]
    return InlineKeyboardMarkup(keyboard)

def get_restart_keyboard() -> InlineKeyboardMarkup:
    """Создает клавиатуру для перезапуска"""
    keyboard = [
        [InlineKeyboardButton("Начать заново", callback_data=RESTART)]
    ]
    return InlineKeyboardMarkup(keyboard) 