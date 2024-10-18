FROM maven:3.8.4-openjdk-17 AS build
LABEL authors="carlo"
WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests

# Etapa 2: Runtime
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/bff-0.0.1-SNAPSHOT.jar ./bff-service.jar
EXPOSE 8091

ENTRYPOINT ["java", "-jar", "bff-service.jar"]