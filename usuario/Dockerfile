# Use uma imagem base do OpenJDK
FROM openjdk:17-jdk

# Crie o diretório onde o JAR será copiado
RUN mkdir /usr/app

# Adicione o JAR da aplicação ao container
COPY ./target/usuario-0.0.1-SNAPSHOT.jar /usr/app/

# Defina o diretório de trabalho
WORKDIR /usr/app

# Exponha a porta que a aplicação irá rodar
EXPOSE 8084

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "usuario-service.jar"]

