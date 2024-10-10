Plataforma de Jogos - Serviço de Compras e Avaliações

Descrição
Este projeto é uma plataforma de jogos que permite aos jogadores comprarem e avaliarem jogos disponibilizados por empresas cadastradas. O sistema é baseado em uma arquitetura de microsserviços, com cada funcionalidade como cadastro de usuários, empresas, jogos e avaliações distribuída em diferentes serviços. No futuro, o acoplamento com o Feign Client será removido, garantindo maior independência entre os serviços.

Funcionalidades Principais
Cadastro de Usuários: O sistema suporta diferentes tipos de usuários: Jogador, Empresa, e Admin.
Cadastro de Empresas: Empresas podem registrar seus jogos e gerenciar as informações de venda.
Compra de Jogos: Jogadores podem comprar jogos de empresas cadastradas.
Avaliação de Jogos: Após a compra, os jogadores podem avaliar os jogos.
Biblioteca de Jogos: Jogadores têm uma biblioteca onde os jogos comprados ficam armazenados.

Tecnologias Utilizadas
  -Java (Spring Boot): Implementação dos microsserviços.
  -Feign Client: Utilizado atualmente para comunicação entre microsserviços.
  -RabbitMQ: Mensageria assíncrona para comunicação entre serviços.
  -Docker: Cada serviço foi containerizado usando Docker.
  -Kubernetes: Orquestração dos containers em um ambiente Kubernetes.
  -Banco de Dados: Cada microsserviço possui seu banco de dados independente, garantindo maior separação de responsabilidades.

Microsserviços
1. Serviço de Usuários
  -Responsabilidade: Gerencia o cadastro e manipulação de usuários.
  -Modelo: Usuario
    -Atributos principais: id, nome, email, senha, ativo, tipoUsuario.
    -Tipos de usuários: Jogador, Empresa, Admin.
  -Serviço: UsuarioService
    -Métodos principais: Criar, Atualizar, Deletar, Ativar/Desativar usuários.
  -Fila RabbitMQ: Mensagens para filas específicas de Empresa e Jogador, dependendo do tipo de usuário.

2. Serviço de Empresas
  -Responsabilidade: Cadastro de empresas e gerenciamento de informações.
  -Modelo: Empresa
    -Atributos principais: id, usuarioId, razaoSocial, cnpj, ativo.
  -Serviço: EmpresaService
    -Métodos principais: Criar, Atualizar, Deletar, Ativar/Desativar empresas.

3. Serviço de Jogos
  -Responsabilidade: Gerenciamento de jogos oferecidos pelas empresas.
  -Modelo: Jogo
    -Atributos principais: id, nome, descricao, preco, usuarioId, cnpj, razaoSocial.
  -Serviço: JogoService
   -Métodos principais: Criar, Atualizar, Deletar jogos.
   -Integração com o serviço de Empresa para validar CNPJ e razão social.

4. Serviço de Biblioteca de Jogos
  -Responsabilidade: Gerenciar a biblioteca de jogos adquiridos pelos jogadores.
  -Modelo: Biblioteca
    -Atributos principais: id, usuarioId, jogos.
  -Serviço: BibliotecaService
    -Métodos principais: Adicionar jogos à biblioteca.

Comunicação entre Microsserviços
Atualmente, o Feign Client é utilizado para comunicação entre os microsserviços. Ele facilita a integração entre os serviços, mas o objetivo futuro é desacoplar completamente essa dependência, possivelmente utilizando eventos assíncronos via RabbitMQ para promover maior independência entre os serviços.

Docker e Kubernetes
Docker
Cada microsserviço foi containerizado, permitindo fácil deploy e escalabilidade. Você pode construir e rodar cada serviço usando o Docker.
# Exemplo de build e run de um microsserviço
mvn clean package -DskipTests
docker build -t usuario-service:latest .
docker tag usuario-service:latest carwilfer/usuario-service:latest
docker push carwilfer/usuario-service:latest

Kubernetes
Para orquestrar os containers, o sistema está configurado para rodar em um ambiente Kubernetes. As configurações de Kubernetes foram feitas para garantir a alta disponibilidade e a fácil escalabilidade do sistema, dividindo os serviços em pods independentes.
# Exemplo de aplicação de configuração de deploy no Kubernetes
kubectl apply -f rabbitmq-deployment.yaml
kubectl port-forward deployments/rabbitmq 15673:15672

kubectl apply -f postgres-deployment.yaml
kubectl port-forward deployments/postgres 5433:5432

kubectl apply -f zipkin-deployment.yaml
kubectl port-forward deployments/zipkin 9411:9411

kubectl apply -f usuario-deployment.yaml
kubectl port-forward service/usuario-service 8084:8084

Futuras Melhorias
  -Desacoplar Feign Client: Utilizar eventos assíncronos para comunicação entre serviços via RabbitMQ.
  -Escalabilidade: Implementar auto-scaling baseado na carga de usuários.
  -Autenticação e Autorização: Implementar OAuth2 para autenticação centralizada entre os microsserviços.
  -Testes Automatizados: Melhorar a cobertura de testes unitários e de integração.
  
