#FROM maven:3.6.3-openjdk-17
#WORKDIR cinema
#COPY . .
#RUN mvn package spring-boot:repackage -Dmaven.test.skip=true
#CMD ["java", "-jar", "target/cinema-1.0-SNAPSHOT.jar"]
# Этап сборки
# Этап сборки
FROM maven:3.6.3-jdk-17 AS builder
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
COPY resources ./resources  # Копируем ресурсы, включая конфиги
RUN mvn package spring-boot:repackage -Dmaven.test.skip=true

# Финальный образ
FROM openjdk:17-jdk-slim
WORKDIR /app

# Копируем собранный JAR и ресурсы
COPY --from=builder /build/target/cinema-1.0-SNAPSHOT.jar .
COPY --from=builder /build/resources ./resources

# Создаем директорию для загружаемых файлов
RUN mkdir -p /app/files && \
    chmod -R 755 /app/files && \
    adduser --system --group cinema && \
    chown -R cinema /app

USER cinema

# Переопределяем настройки через переменные окружения
ENV SPRING_CONFIG_LOCATION=file:/app/resources/
ENV SPRING_CONFIG_IMPORT=optional:file:/app/resources/
ENV SPRING_SERVLET_MULTIPART_MAX_FILE_SIZE=10MB
ENV FILE_DIRECTORY=/app/files

HEALTHCHECK --interval=30s --timeout=5s \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

CMD ["java", "-Xmx1g", "-XX:+UseContainerSupport", "-jar", "cinema-1.0-SNAPSHOT.jar"]