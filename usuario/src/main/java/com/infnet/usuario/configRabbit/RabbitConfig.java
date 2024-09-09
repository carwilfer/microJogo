package com.infnet.usuario.configRabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.util.ErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@EnableRabbit
public class RabbitConfig {

    public static final String QUEUE_NAME = "usuarioQueue";
    public static final String EXCHANGE_NAME = "usuarioExchange";
    public static final String ROUTING_KEY = "usuario.#";

    public static final String EMPRESA_QUEUE_NAME = "empresaQueue";
    public static final String EMPRESA_EXCHANGE_NAME = "empresaExchange";
    public static final String EMPRESA_ROUTING_KEY = "empresa.#";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }
    //para a fila da empresa
    @Bean
    public Queue empresaQueue() {
        return new Queue(EMPRESA_QUEUE_NAME, true); // true para durable
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }
    //para a fila da empresa
    @Bean
    public TopicExchange empresaExchange() {
        return new TopicExchange(EMPRESA_EXCHANGE_NAME);
    }

    @Bean
    public Binding bindingUsuario(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }
    //para a fila da empresa
    @Bean
    public Binding bindingEmpresaQueue(Queue empresaQueue, TopicExchange empresaExchange) {
        return BindingBuilder.bind(empresaQueue).to(empresaExchange).with(EMPRESA_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        return factory;
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, UsuarioListener usuarioListener) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(new MessageListenerAdapter(usuarioListener, "processarMensagem"));

        // Configura um manipulador de erros personalizado
        container.setErrorHandler(new ErrorHandler() {
            private final Logger logger = LoggerFactory.getLogger(RabbitConfig.class);

            @Override
            public void handleError(Throwable t) {
                logger.error("Erro ao processar mensagem: {}", t.getMessage(), t);
                // Implementar lógica de tratamento de exceções aqui
            }
        });

        return container;
    }
}
