#!/bin/bash

# Passo 1: Build dos projetos Maven para os microsserviços
echo "Executando mvn clean package para os microsserviços..."

# Compilar todos os serviços necessários
mvn clean package -DskipTests

# Passo 2: Construir e enviar as imagens Docker para cada microsserviço
echo "Construindo e enviando imagens Docker..."

# Construir e enviar imagem Docker do microsserviço usuario
echo "Construindo imagem Docker do serviço usuario..."
docker build -t usuario:latest .
docker tag usuario-service:latest carwilfer/usuario-service:latest
docker push carwilfer/usuario-service:latest

# Construir e enviar imagem Docker do microsserviço avaliacao
echo "Construindo imagem Docker do serviço avaliacao..."
docker build -t avaliacao:latest .
docker tag avaliacao-service:latest carwilfer/avaliacao-service:latest
docker push carwilfer/avaliacao-service:latest


# Construir e enviar imagem Docker do microsserviço compra
echo "Construindo imagem Docker do serviço compra..."
docker build -t compra:latest .
docker tag compra-service:latest carwilfer/compra-service:latest
docker push carwilfer/compra-service:latest

# Construir e enviar imagem Docker do microsserviço conta
echo "Construindo imagem Docker do serviço conta..."
docker build -t conta:latest .
docker tag conta-service:latest carwilfer/conta-service:latest
docker push carwilfer/conta-service:latest

# Construir e enviar imagem Docker do microsserviço empresa
echo "Construindo imagem Docker do serviço empresa..."
docker build -t empresa:latest .
docker tag empresa-service:latest carwilfer/empresa-service:latest
docker push carwilfer/empresa-service:latest

# Construir e enviar imagem Docker do microsserviço jogador
echo "Construindo imagem Docker do serviço jogador..."
docker build -t jogador:latest .
docker tag jogador-service:latest carwilfer/jogador-service:latest
docker push carwilfer/jogador-service:latest

# Construir e enviar imagem Docker do microsserviço jogo
echo "Construindo imagem Docker do serviço jogo..."
docker build -t jogo:latest .
docker tag jogo-service:latest carwilfer/jogo-service:latest
docker push carwilfer/jogo-service:latest

# Passo 3: Aplicar as configurações para os componentes de infraestrutura necessários antes dos microsserviços
echo "Aplicando configurações para Fluentd, Elasticsearch, Kibana..."

# Aplicar ConfigMap do Fluentd
kubectl apply -f fluentd-configmap.yaml

# Aplicar Elasticsearch, Kibana, e Fluentd DaemonSet
kubectl apply -f elasticsearch-service.yaml
kubectl apply -f kibana-deployment.yaml
kubectl apply -f fluentd-daemonset.yaml

# Esperar alguns segundos para garantir que Elasticsearch e Kibana iniciem corretamente
echo "Aguardando Elasticsearch e Kibana iniciarem..."
sleep 30

# Passo 4: Aplicar PostgreSQL, RabbitMQ, e outros serviços de infraestrutura
echo "Aplicando PostgreSQL e RabbitMQ..."
kubectl apply -f postgres-deployment.yaml
kubectl apply -f rabbitmq-deployment.yaml

# Esperar alguns segundos para garantir que o banco de dados e RabbitMQ estejam prontos
echo "Aguardando PostgreSQL e RabbitMQ iniciarem..."
sleep 30

# Passo 5: Aplicar os microsserviços na sequência correta
echo "Aplicando os microsserviços..."

kubectl apply -f avaliacao-deployment.yaml
kubectl apply -f compra-deployment.yaml
kubectl apply -f conta-deployment.yaml
kubectl apply -f empresa-deployment.yaml
kubectl apply -f jogador-deployment.yaml
kubectl apply -f jogo-deployment.yaml
kubectl apply -f usuario-deployment.yaml

# Passo 6: Verificar o status de todos os pods após o deploy
echo "Verificando status dos pods..."
kubectl get pods

# Conclusão
echo "Todos os deployments e serviços foram aplicados com sucesso."