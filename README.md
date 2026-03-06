# User Service (Java + Hibernate + PostgreSQL)

Консольное приложение для управления пользователями (CRUD) без использования Spring.

## Стек технологий
* **Java 17+**
* **Hibernate 6** (ORM)
* **PostgreSQL** (в Docker)
* **Maven** (сборка проекта)

## Как запустить базу данных
Для работы приложения необходимо запустить PostgreSQL в Docker контейнере:
```bash
docker run --name user-service-db -e POSTGRES_DB=users_db -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=root -p 5433:5432 -d postgres
