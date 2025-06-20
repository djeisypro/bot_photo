# Telegram Photo Bot

Этот бот Telegram предоставляет мощные и удобные инструменты для обработки фотографий. Он позволяет пользователям применять различные эффекты, обрезать изображения и управлять фотографиями как в одиночном, так и в групповом режиме.

## Описание проекта

Проект представляет собой Telegram-бота, разработанного на Python с использованием библиотеки `python-telegram-bot` и `Pillow` для обработки изображений. Бот ориентирован на простоту использования и функциональность, предоставляя пользователям широкий спектр опций для улучшения и трансформации их фотографий прямо через интерфейс Telegram.

## Основные возможности

### Режимы работы

1.  **Одиночная фотография**: Пользователь отправляет одно фото, которое затем может быть последовательно обработано различными эффектами и форматами обрезки.
2.  **Групповая обработка фотографий**: Пользователь может отправить несколько фотографий, после чего к ним будет применен выбранный эффект. (Обратите внимание: текущая реализация сфокусирована на одиночной обработке для последовательного применения эффектов и обрезки).

### Эффекты обработки

Бот поддерживает следующие эффекты, которые можно применять к фотографиям:

*   **Общее улучшение (`enhance`)**: Увеличивает контрастность, яркость и насыщенность цветов изображения, делая его более живым и четким.
*   **Инверсия (`invert`)**: Инвертирует цвета изображения, создавая эффект негатива.
*   **Черно-белое (`bw`)**: Преобразует изображение в градации серого, убирая все цветовые оттенки.
*   **Мультфильм (`cartoon`)**: Создает эффект мультяшного рисунка, усиливая края, уменьшая количество цветов и повышая насыщенность.
*   **Виньетка (`vignette`)**: Добавляет эффект затемнения по краям изображения, фокусируя внимание на центре.
*   **Карандаш (`pencil`)**: Превращает фотографию в стилизованный карандашный рисунок с эффектами размытия и смешивания.
*   **Соляризация (`solarize`)**: Создает эффект частичной инверсии цветов, инвертируя пиксели, яркость которых выше определенного порога.
*   **Пикселизация (`pixelate`)**: Уменьшает разрешение изображения и затем увеличивает его обратно, создавая эффект пиксельной графики.

### Функции обрезки

Бот предлагает несколько стандартных форматов обрезки, чтобы пользователи могли легко подготовить фотографии для различных нужд (аватарки, обои и т.д.):

*   **Квадрат (аватарка)**: Обрезает изображение до квадратного формата (соотношение сторон 1:1).
*   **Продолговатая (обои телефона)**: Обрезает изображение до портретного формата (соотношение сторон 9:16), идеально подходит для экранов смартфонов.
*   **Продолговатая (обои компьютера)**: Обрезает изображение до альбомного формата (соотношение сторон 16:9), подходит для экранов компьютеров и широкоформатных дисплеев.

### Управление файлами

Бот автоматически обрабатывает временные файлы:
*   Скачанные и обработанные фотографии временно сохраняются на вашем устройстве.
*   После завершения сессии (команды `/stop` или `/restart`) или после новой обработки/обрезки предыдущий обработанный файл **автоматически удаляется**, чтобы избежать накопления ненужных данных на диске.

## Как использовать

Следуйте этим шагам, чтобы начать работу с ботом и обрабатывать свои фотографии:

1.  **Начало работы**:
    *   Отправьте команду `/start` в чате с ботом.
    *   Бот предложит выбрать режим работы: "Одна фотография" или "Группа фотографий".

2.  **Обработка одной фотографии**:
    *   Выберите "Одна фотография".
    *   Отправьте фотографию, которую хотите обработать.
    *   Бот предложит варианты: "Обработка" или "Обрезка".
    *   **Применение эффектов**:
        *   Нажмите "Обработка".
        *   Бот покажет клавиатуру с доступными эффектами.
        *   Используйте кнопки "Вперёд ▶️" и "⬅️" для навигации по страницам эффектов.
        *   Выберите желаемый эффект. Бот отправит вам обработанное изображение.
        *   После обработки бот предложит "Обрезать это фото", "Обработать это фото" (повторно с другим эффектом) или "Начать заново".
    *   **Обрезка фотографии**:
        *   Нажмите "Обрезка".
        *   Выберите желаемый формат обрезки (Квадрат, Портрет, Пейзаж). Бот автоматически обрежет фото и отправит результат.
        *   После обрезки бот предложит "Обработать это фото", "Обрезать это фото" (повторно с другим форматом) или "Начать заново".

3.  **Групповая обработка фотографий**:
    *   Выберите "Группа фотографий".
    *   Отправьте все фотографии, которые вы хотите обработать (по одной).
    *   Когда все фотографии отправлены, отправьте команду `/process`.
    *   Бот покажет клавиатуру с эффектами. Выберите один эффект, и он будет применен ко всем загруженным фотографиям.
    *   (Текущая реализация в основном поддерживает выбор одного эффекта для группы).

4.  **Повторная обработка или обрезка**:
    *   После каждой обработки или обрезки бот предлагает меню с опциями "Обработать это фото" (применить другой эффект к текущему результату), "Обрезать это фото" (обрезать текущий результат) или "Начать заново". Это позволяет строить цепочки обработки.

## Команды бота

*   `/start`: Начинает новую сессию с ботом, очищает все предыдущие данные.
*   `/process`: Используется для начала обработки фотографий в групповом режиме или для вызова меню эффектов, если фото уже загружено.
*   `/stop`: Завершает текущую сессию, очищает все временные файлы и пользовательские данные.
*   `/restart`: Перезапускает бота, очищает все временные файлы и пользовательские данные, возвращаясь к стартовому меню.

## Технические детали

### Требования

Для запуска бота необходимы:
*   Python 3.7+
*   Библиотеки, указанные в `requirements.txt`:
    *   `python-telegram-bot`
    *   `Pillow`
    *   `numpy`

### Установка

1.  **Клонируйте репозиторий**:
    ```bash
    git clone https://github.com/djeisypro/bot_photo.git
    cd bot_photo
    ```

2.  **Установите зависимости**:
    ```bash
    pip install -r requirements.txt
    ```

3.  **Настройте токен бота**:
    *   Создайте файл `config.py` в корневой директории проекта.
    *   Получите токен своего бота у BotFather в Telegram.
    *   Добавьте токен в `config.py` следующим образом:
        ```python
        BOT_TOKEN = "ВАШ_ТОКЕН_БОТА"
        ```

4.  **Запустите бота**:
    ```bash
    python bot.py
    ```

## Структура проекта

*   `bot.py`: Основной файл, инициализирует бота и регистрирует все обработчики команд и колбэков.
*   `handlers.py`: Содержит логику обработки всех команд и взаимодействий пользователя (получение фото, применение эффектов, обрезка, навигация).
*   `keyboards.py`: Отвечает за создание всех инлайн-клавиатур и кнопок, используемых в боте.
*   `effects.py`: Содержит функции для применения различных эффектов к изображениям с использованием библиотеки Pillow.
*   `constants.py`: Определяет константы (строковые идентификаторы) для callback-данных, режимов работы и других настроек, обеспечивая чистоту кода.
*   `utils.py`: Вспомогательные функции, включая логику очистки временных файлов и генерации путей для сохранения.
*   `config.py`: Файл для хранения конфиденциальных данных, таких как токен бота.

## Особенности

*   **Интуитивный интерфейс**: Использование инлайн-клавиатур делает взаимодействие с ботом простым и понятным.
*   **Модульная структура**: Код разбит на логические модули, что упрощает дальнейшую разработку и поддержку.
*   **Автоматическая очистка**: Временные файлы фотографий удаляются после обработки или завершения сессии, не засоряя дисковое пространство.
*   **Последовательная обработка**: Возможность применять несколько эффектов или операций обрезки к одному и тому же изображению по цепочке.
*   **Обработка ошибок**: Включено базовое логирование ошибок для отслеживания проблем. 