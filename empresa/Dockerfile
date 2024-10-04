FROM openjdk:17-jdk-slim
LABEL authors="carlo"
WORKDIR /app
COPY . .

COPY target/empresa-0.0.1-SNAPSHOT.jar ./empresa-service.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "empresa-service.jar"]