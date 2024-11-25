FROM openjdk:17-jdk-slim

WORKDIR /app

COPY special/Modsen_Java_Library-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]