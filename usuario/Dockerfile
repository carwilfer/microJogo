# Estágio de build com Maven
FROM maven:3.8.4-openjdk-17 AS build
LABEL authors="carlo"
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Estágio de execução com OpenJDK
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/usuario-0.0.1-SNAPSHOT.jar ./usuario-service.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "usuario-service.jar"]

