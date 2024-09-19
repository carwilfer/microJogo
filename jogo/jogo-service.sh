# Passo 1: Build dos projetos Maven para os microsserviços
echo "Executando mvn clean package para os microsserviços..."

# Compilar todos os serviços necessários
mvn clean package -DskipTests

# Passo 2: Construir e enviar as imagens Docker para cada microsserviço
echo "Construindo e enviando imagens Docker..."

# Construir e enviar imagem Docker do microsserviço jogo
echo "Construindo imagem Docker do serviço jogo..."
docker build -t jogo:latest .
docker tag jogo-service:latest carwilfer/jogo-service:latest
docker push carwilfer/jogo-service:latest