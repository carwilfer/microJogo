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
sleep 10
kubectl apply -f compra-deployment.yaml
sleep 10
kubectl apply -f conta-deployment.yaml
sleep 10
kubectl apply -f empresa-deployment.yaml
sleep 10
kubectl apply -f jogador-deployment.yaml
sleep 10
kubectl apply -f jogo-deployment.yaml
sleep 10
kubectl apply -f usuario-deployment.yaml

# Passo 6: Verificar o status de todos os pods após o deploy
echo "Verificando status dos pods..."
kubectl get pods

# Conclusão
echo "Todos os deployments e serviços foram aplicados com sucesso."