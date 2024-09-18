# Use uma imagem base do Maven para construir o projeto
FROM maven:3.8.4-openjdk-17 AS build
LABEL authors="carlo"
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Use uma imagem base do OpenJDK para rodar o microsserviço
# Estágio de execução
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/jogador-0.0.1-SNAPSHOT.jar ./jogador-service.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "jogador-service.jar"]