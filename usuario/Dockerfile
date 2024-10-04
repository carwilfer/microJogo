# Usando a imagem base oficial do Java OpenJDK 17
FROM openjdk:17-jdk
LABEL authors="carlo"

# Definir diretório de trabalho no container
WORKDIR /app

# Copiar todos os arquivos do projeto para o diretório de trabalho
COPY . .

# Certifique-se de que o arquivo JAR está no caminho correto
# O arquivo JAR deve ser gerado pelo Maven antes de construir a imagem
COPY target/usuario-0.0.1-SNAPSHOT.jar ./usuario-service.jar

# Expor a porta 8084
EXPOSE 8084

# Definir o comando de inicialização
ENTRYPOINT ["java", "-jar", "usuario-service.jar"]
