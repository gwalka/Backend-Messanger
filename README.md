# Прототип Мессенджера

## Необходимое окружение

-   Git
-   Docker
-   Docker Compose
-   Flutter SDK (для запуска Flutter-приложения)


## О проекте
Прототип мессенджера с минимальным функционалом:

-   JWT - для аутентификации и защиты эндпоинтов
-   PostgreSQL база данных
-   REST API
-   WebSocket для передачи сообщений в реальном времени (на данном этапе реализован только онлайн пуш сообщений, когда оба пользователя подписаны на WebSocket)

### API:

-   Регистрация
-   Логин
-   Поиск людей по юзернейму
-   Создание чата
-   Отправка сообщений

## Инструкция по запуску

### 1. Запуск Backend (Java + PostgreSQL)
```bash
git clone https://github.com/gwalka/Backend-Messanger.git
```
```bash
cd Backend-Messanger
```
```bash
./gradlew clean bootJar
```
```bash
docker-compose up --build
```
```bash
nc -zv localhost 8080
```
Если видите сообщение `Connection to localhost port 8080 [tcp/http-alt] succeeded!`, значит, приложение и база данных запущены и готовы к работе.

### 2. Запуск Frontend (Flutter)
```bash
git clone https://github.com/gwalka/Front-Messanger.git
```
```bash
cd Front-Messanger
```
```bash
flutter pub get
```

```bash
flutter run
```
Выбираем [2]: Chrome (chrome)
Можно протестировать функционал.

## Валидация данных при регистрации:

-   **Email:** Только валидный email формата `example@gmail.com`
-   **Username:** Только буквы и цифры (например, `Username123`)
-   **Password:** Минимум 6 символов, наличие заглавной буквы, строчной буквы и спецсимвола (например, `Password1!`)

<img width="678" height="650" alt="Снимок экрана 2025-10-28 в 00 06 57" src="https://github.com/user-attachments/assets/29374473-0cd7-410b-b187-77c2b9a9ae51" /> <img width="684" height="634" alt="Снимок экрана 2025-10-28 в 00 07 33" src="https://github.com/user-attachments/assets/f852e952-8284-47b4-9ce7-ec319515c07c" />
<img width="693" height="609" alt="Снимок экрана 2025-10-28 в 00 08 48" src="https://github.com/user-attachments/assets/5a6bdf8f-0d89-4a5b-95e5-ec93ae0bf7b6" /> <img width="978" height="656" alt="Снимок экрана 2025-10-28 в 00 10 45" src="https://github.com/user-attachments/assets/10b317b7-9912-45ba-a496-5cede574b2a7" />
