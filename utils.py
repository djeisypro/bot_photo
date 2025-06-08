import os
import glob
import logging
from typing import Optional

async def cleanup_temp_files(context) -> None:
    """
    Удаляет временные файлы, созданные ботом.
    
    Args:
        context: Контекст бота
    """
    user_id = context.effective_user.id
    patterns = [
        f"temp_{user_id}_*.jpg",
        f"processed_temp_{user_id}.jpg",
        f"processed_temp_{user_id}.png",
        f"cropped_temp_{user_id}.jpg",
        f"cropped_temp_{user_id}.png"
    ]
    
    for pattern in patterns:
        try:
            for file_path in glob.glob(pattern):
                try:
                    os.remove(file_path)
                except Exception as e:
                    logging.error(f"Ошибка при удалении файла {file_path}: {e}")
        except Exception as e:
            logging.error(f"Ошибка при поиске файлов по шаблону {pattern}: {e}")

def get_output_path(photo_path: str, prefix: str, user_id: Optional[int] = None) -> str:
    """
    Генерирует путь для сохранения обработанного изображения.
    
    Args:
        photo_path: Путь к исходному изображению
        prefix: Префикс для имени файла
        user_id: ID пользователя (опционально)
        
    Returns:
        Путь для сохранения обработанного изображения
    """
    # Получаем расширение файла
    _, ext = os.path.splitext(photo_path)
    
    # Формируем имя файла
    if user_id is not None:
        filename = f"{prefix}_temp_{user_id}{ext}"
    else:
        filename = f"{prefix}_{os.path.basename(photo_path)}"
    
    return filename 