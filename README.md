User Service (Java + Hibernate + PostgreSQL)
Консольное приложение для управления пользователями (CRUD) без использования Spring. Реализовано с соблюдением многослойной архитектуры и покрыто тестами.
Стек технологий
Java 17+
Hibernate 6 (ORM)
PostgreSQL (в Docker)
Maven (сборка проекта)
JUnit 5, Mockito & Testcontainers (тестирование)
Как запустить базу данных
Для работы приложения необходимо запустить PostgreSQL в Docker контейнере:
bash
docker run --name user-service-db -e POSTGRES_DB=users_db -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=root -p 5433:5432 -d postgres:15
Используйте код с осторожностью.

Сборка и запуск
Клонируйте репозиторий:
bash
git clone https://github.com
Используйте код с осторожностью.

Соберите проект и запустите тесты:
bash
mvn clean install
Используйте код с осторожностью.

Запустите приложение:
bash
mvn exec:java -Dexec.mainClass="Main"
Используйте код с осторожностью.

Тестирование
Проект содержит два типа тестов:
Unit-тесты: Проверка бизнес-логики UserService с использованием моков.
Интеграционные тесты: Тестирование UserDao на реальной БД через Testcontainers.