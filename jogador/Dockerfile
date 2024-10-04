FROM openjdk:17-jdk-slim
LABEL authors="carlo"
WORKDIR /app
COPY . .

COPY target/jogador-0.0.1-SNAPSHOT.jar ./jogador-service.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "jogador-service.jar"]