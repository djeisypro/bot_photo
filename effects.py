from PIL import Image, ImageEnhance, ImageFilter, ImageOps
import numpy as np

def apply_effect(image: Image.Image, effect_type: str) -> Image.Image:
    """
    Применяет выбранный эффект к изображению.
    
    Args:
        image: Исходное изображение
        effect_type: Тип эффекта для применения
        
    Returns:
        Обработанное изображение
    """
    if effect_type in ['enhance', 'enhance_last']:
        # Улучшение контраста, яркости и цветов
        enhancer = ImageEnhance.Contrast(image)
        image = enhancer.enhance(1.5)
        enhancer = ImageEnhance.Brightness(image)
        image = enhancer.enhance(1.2)
        enhancer = ImageEnhance.Color(image)
        image = enhancer.enhance(1.3)
        
    elif effect_type in ['invert', 'invert_last']:
        # Инверсия цветов
        image = ImageOps.invert(image)
        
    elif effect_type in ['bw', 'bw_last']:
        # Черно-белое изображение
        image = image.convert('L')
        
    elif effect_type in ['cartoon', 'cartoon_last']:
        # Эффект мультфильма
        # Усиливаем края
        edges = image.filter(ImageFilter.EDGE_ENHANCE_MORE)
        # Уменьшаем количество цветов
        image = image.quantize(colors=8).convert('RGB')
        # Увеличиваем насыщенность
        enhancer = ImageEnhance.Color(image)
        image = enhancer.enhance(1.5)
        # Смешиваем с краями
        image = Image.blend(image, edges, 0.3)
        
    elif effect_type in ['vignette', 'vignette_last']:
        # Эффект виньетки
        # Создаем маску для виньетки
        width, height = image.size
        mask = Image.new('L', (width, height), 0)
        
        # Рисуем белый эллипс в центре
        from PIL import ImageDraw
        draw = ImageDraw.Draw(mask)
        draw.ellipse([0, 0, width, height], fill=255)
        
        # Размываем маску
        mask = mask.filter(ImageFilter.GaussianBlur(radius=width/4))
        
        # Создаем черное изображение для наложения
        overlay = Image.new('RGB', (width, height), (0, 0, 0))
        
        # Накладываем маску
        image = Image.composite(image, overlay, mask)
        
    elif effect_type in ['pencil', 'pencil_last']:
        # Эффект карандашного рисунка
        # Конвертируем в оттенки серого
        gray = image.convert('L')
        # Инвертируем
        inverted = ImageOps.invert(gray)
        # Размываем
        blurred = inverted.filter(ImageFilter.GaussianBlur(radius=2))
        # Смешиваем с оригиналом
        image = Image.blend(gray, blurred, 0.5)
        
    elif effect_type in ['solarize', 'solarize_last']:
        # Эффект соляризации
        image = ImageOps.solarize(image, threshold=128)
        
    elif effect_type in ['pixelate', 'pixelate_last']:
        # Эффект пикселизации
        # Уменьшаем размер
        width, height = image.size
        small = image.resize((width//8, height//8), resample=Image.NEAREST)
        # Увеличиваем обратно
        image = small.resize((width, height), resample=Image.NEAREST)
    
    return image 