Сервис управления пользователями и их подписками

# Требования

Для запуска проекта убедитесь, что у вас установлены следующие инструменты:
Java: JDK 17 или выше
Gradle: 7.5 или выше (или используйте ./gradlew из проекта)
Docker: версия с поддержкой Docker Compose
cURL или Postman (для тестирования API)

# Структура проекта

user-service: Основной модуль Spring Boot, содержащий API и логику приложения.
docker-compose.yml: Конфигурация для запуска приложения и базы данных PostgreSQL.
.env.local: Файл с переменными окружения (пример предоставлен ниже).

# Установка и запуск
Настроить переменные окружения: .env.local
Используйте Gradle для сборки приложения: ./gradlew clean build
Запустить приложение с помощью Docker Compose: docker-compose -f docker-compose.yml --env-file .env.local up --build -d
    Должно создаться 2 контейнера: usersubscriptionservice-db-1 - PostgreSQL база данных.
                                   usersubscriptionservice-user-service-1 - Spring Boot приложение

# Тестирование API
API доступно по адресу http://localhost:8080. Используйте cURL или Postman для отправки запросов.
1. Создать пользователя

curl -X POST http://localhost:8080/users \
-H "Content-Type: application/json" \
-d '{"name":"John Doe","email":"john@example.com"}'

2. Добавить подписку для пользователя

curl -X POST http://localhost:8080/users/1/subscriptions \
-H "Content-Type: application/json" \
-d '{"type":"PREMIUM","startDate":"2025-05-07T10:00:00","endDate":"2025-06-07T10:00:00"}'

3. Получить все подписки пользователя

curl http://localhost:8080/subscriptions/user/1

4. Удалить подписку

curl -X DELETE http://localhost:8080/subscriptions/user/1/1