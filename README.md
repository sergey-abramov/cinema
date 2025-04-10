# job4j_cinema

---

## О проекте

Web-приложение "Кинотеатр".

Технологии
Основной стек:
```
-Java 17
-Spring Boot 2.7.6
-Spring Web MVC
-Thymeleaf (шаблонизатор)
-Spring Boot DevTools
-PostgreSQL 12 (основная БД)
-H2 Database (для тестов)
-Liquibase (миграции БД)
-Apache DBCP2 (пул соединений)
-SQL2O (упрощенный доступ к БД)
```

Тестирование:
```
-JUnit 4/5
-Mockito
-AssertJ
-Hamcrest
-Jacoco (покрытие кода)
```

Инструменты:
```
-Maven
-Checkstyle (валидация кода)
-Liquibase Maven Plugin
```

Требования
Для запуска проекта необходимо:

```
-JDK 17+
-Maven 3.6+
-Docker (для контейнеризации)
-PostgreSQL 12+ (или Docker)
```

Запуск приложения
1. Сборка и запуск через Maven:
   ```bash 
   mvn spring-boot:run
   ```
2. Запуск в Docker:

   Собрать образ:

    ```bash
    docker-compose build
    ```
    Запустить сервисы:

    ```bash
    docker-compose up
   ```
    Приложение будет доступно по адресу: http://localhost:8080


3. Администрирование БД:
   PGAdmin доступен по адресу: http://localhost:5050
   Учетные данные:
```
Email: cinema@mail.com
Пароль: pgadminpwd4cinema
```

Конфигурация
Основные настройки приложения:
```
Порт: 8080
Максимальный размер загружаемых файлов: 10MB
Директория для файлов: files/ (внутри контейнера - /app/files)
```

Настройки БД (можно переопределить через переменные окружения):
```
URL: jdbc:postgresql://postgres_db:5432/cinema
Пользователь: postgres
Пароль: password
```

Профили Maven
```
test (активен по умолчанию):
Использует тестовую конфигурацию Liquibase
Подходит для разработки
production:
Использует production конфигурацию Liquibase
Активируется флагом -Pproduction
```

Сборка
Для сборки JAR-файла:

```bash
mvn clean package
```
Собранный JAR будет доступен в target/cinema.jar

Тестирование
Запуск тестов:

```bash
mvn test
```
Генерация отчета о покрытии кода (Jacoco):

```bash
mvn jacoco:report
```
Отчет будет доступен в target/site/jacoco/

Проверка стиля кода
Запуск Checkstyle:

```bash
mvn checkstyle:check
```

Миграции базы данных
Применение миграций Liquibase:

```bash
mvn liquibase:update
```
Структура проекта
Основные пакеты:

```
ru.job4j.cinema - точка входа (Main класс)
ru.job4j.cinema.config - конфигурация Spring
ru.job4j.cinema.controller - контроллеры
ru.job4j.cinema.model - доменные модели
ru.job4j.cinema.repository - репозитории/DAO
ru.job4j.cinema.service - бизнес-логика
ru.job4j.cinema.util - утилиты
```

Особенности Docker-развертывания
Используется multi-stage сборка для уменьшения размера образа

PostgreSQL и PGAdmin запускаются в отдельных контейнерах

Загруженные файлы сохраняются в Docker volume

Healthcheck для мониторинга состояния сервисов

---

## Функционал

### Главная страница

_**Страница входа**_:
![ ](https://github.com/sergey-abramov/job4j_cinema/blob/e86fa34a1b12ca4a3021b3b57318f5760001ba23/files/%D0%B2%D1%85%D0%BE%D0%B4.png)

_**Страница регистрации**_:
![ ](https://github.com/sergey-abramov/job4j_cinema/blob/e86fa34a1b12ca4a3021b3b57318f5760001ba23/files/%D1%80%D0%B5%D0%B3%D0%B8%D1%81%D1%82%D1%80%D0%B0%D1%86%D0%B8%D1%8F.png)

_**Страница c сеансами**_:
![ ](https://github.com/sergey-abramov/job4j_cinema/blob/e86fa34a1b12ca4a3021b3b57318f5760001ba23/files/%D1%81%D0%B5%D0%B0%D0%BD%D1%81%D1%8B.png)

_**Страница с фильмами**_:
![ ](https://github.com/sergey-abramov/job4j_cinema/blob/e86fa34a1b12ca4a3021b3b57318f5760001ba23/files/%D1%81%D1%82%D1%80%D0%B0%D0%BD%D0%B8%D1%86%D0%B0%20%D1%81%20%D1%84%D0%B8%D0%BB%D1%8C%D0%BC%D0%B0%D0%BC%D0%B8.png)