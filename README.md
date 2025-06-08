# Telegram Bot для обработки фотографий

Этот бот позволяет обрабатывать фотографии с помощью различных эффектов и обрезки.

## Возможности

- Обработка одной фотографии или группы фотографий
- Различные эффекты обработки:
  - Улучшение (контраст, яркость, цвета)
  - Инверсия цветов
  - Черно-белое изображение
  - Эффект мультфильма
  - Виньетка
  - Эффект карандашного рисунка
  - Соляризация
  - Пикселизация
- Обрезка фотографий в различных форматах:
  - Квадрат
  - Портрет (9:16)
  - Пейзаж (16:9)

## Установка

1. Клонируйте репозиторий:
```bash
git clone <url-репозитория>
cd <директория-проекта>
```

2. Установите зависимости:
```bash
pip install -r requirements.txt
```

3. Создайте файл `config.py` и добавьте в него токен бота:
```python
TOKEN = 'ваш-токен-бота'
```

## Запуск

```bash
python bot.py
```

## Использование

1. Запустите бота в Telegram
2. Отправьте команду `/start`
3. Выберите режим работы (одна фотография или группа фотографий)
4. Следуйте инструкциям бота

## Структура проекта

- `bot.py` - основной файл бота
- `config.py` - конфигурация (токен бота)
- `constants.py` - константы для callback-данных
- `effects.py` - функции обработки изображений
- `handlers.py` - обработчики команд и сообщений
- `keyboards.py` - клавиатуры для интерфейса
- `utils.py` - вспомогательные функции
- `requirements.txt` - зависимости проекта

## Восстановление bot.py

Если файл `bot.py` был утерян или повреждён, вы можете быстро восстановить его следующим образом:

1. Создайте новый файл с именем `bot.py` в корне проекта.
2. Вставьте в него следующий код:

```python
import logging
from telegram.ext import Application, CommandHandler, MessageHandler, CallbackQueryHandler, filters

from config import TOKEN
from handlers import (
    start, mode_select, handle_photo, process, handle_page_navigation,
    process_photos, process_last_result, crop_select, crop_set_mode,
    crop_group_photos, stop, restart
)
from constants import (
    SINGLE_PHOTO, GROUP_PHOTOS, STOP, PROCESS_SINGLE_ASK_PHOTO,
    CROP_SQUARE, CROP_PORTRAIT, CROP_LANDSCAPE, NEXT_PAGE, PREV_PAGE,
    ENHANCE, INVERT, BW, CARTOON, VIGNETTE, PENCIL, SOLARIZE, PIXELATE,
    ENHANCE_LAST, INVERT_LAST, BW_LAST, CARTOON_LAST, VIGNETTE_LAST,
    PENCIL_LAST, SOLARIZE_LAST, PIXELATE_LAST
)

logging.basicConfig(
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s',
    level=logging.INFO
)
logger = logging.getLogger(__name__)

def main():
    application = Application.builder().token(TOKEN).build()

    application.add_handler(CommandHandler("start", start))
    application.add_handler(CommandHandler("process", process))
    application.add_handler(CommandHandler("restart", restart))
    application.add_handler(MessageHandler(filters.PHOTO, handle_photo))
    application.add_handler(CallbackQueryHandler(mode_select, pattern=f"^{SINGLE_PHOTO}$|^{GROUP_PHOTOS}$|^{STOP}$"))
    application.add_handler(CallbackQueryHandler(process, pattern=f"^{PROCESS_SINGLE_ASK_PHOTO}$"))
    application.add_handler(CallbackQueryHandler(handle_page_navigation, pattern=f"^{NEXT_PAGE}$|^{PREV_PAGE}$"))
    application.add_handler(CallbackQueryHandler(process_photos, pattern=f"^{ENHANCE}$|^{INVERT}$|^{BW}$|^{CARTOON}$|^{VIGNETTE}$|^{PENCIL}$|^{SOLARIZE}$|^{PIXELATE}$"))
    application.add_handler(CallbackQueryHandler(process_last_result, pattern=f"^{ENHANCE_LAST}$|^{INVERT_LAST}$|^{BW_LAST}$|^{CARTOON_LAST}$|^{VIGNETTE_LAST}$|^{PENCIL_LAST}$|^{SOLARIZE_LAST}$|^{PIXELATE_LAST}$"))
    application.add_handler(CallbackQueryHandler(crop_select))
    application.add_handler(CallbackQueryHandler(crop_set_mode, pattern=f"^{CROP_SQUARE}$|^{CROP_PORTRAIT}$|^{CROP_LANDSCAPE}$"))
    application.add_handler(CallbackQueryHandler(crop_group_photos))
    application.add_handler(CallbackQueryHandler(stop, pattern=f"^{STOP}$"))
    application.add_handler(CallbackQueryHandler(restart, pattern="^restart$"))

    application.run_polling()

if __name__ == '__main__':
    main()
```

3. Сохраните файл и запустите бота как обычно.

**Вся логика работы бота хранится в отдельных модулях, поэтому восстановление `bot.py` не требует переписывания всей логики!** 