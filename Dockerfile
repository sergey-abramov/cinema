FROM maven:3.6.3-openjdk-17
WORKDIR cinema
COPY . .
RUN mvn package spring-boot:repackage -Dmaven.test.skip=true
CMD ["java", "-jar", "target/cinema-1.0-SNAPSHOT.jar"]
