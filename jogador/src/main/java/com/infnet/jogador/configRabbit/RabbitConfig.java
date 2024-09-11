package com.infnet.jogador.configRabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ErrorHandler;

@Configuration
public class RabbitConfig {

    public static final String JOGADOR_QUEUE = "jogadorQueue";
    public static final String JOGADOR_EXCHANGE_NAME = "jogadorExchange";
    public static final String JOGADOR_ROUTING_KEY = "jogador.#";

    @Bean
    public Queue jogadorQueue() {
        return new Queue(JOGADOR_QUEUE, true);
    }

    @Bean
    public TopicExchange jogadorExchange() {
        return new TopicExchange(JOGADOR_EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue jogadorQueue, TopicExchange jogadorExchange) {
        return BindingBuilder.bind(jogadorQueue).to(jogadorExchange).with(JOGADOR_ROUTING_KEY);
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
        factory.setErrorHandler(new ErrorHandler() {
            private final Logger logger = LoggerFactory.getLogger(RabbitConfig.class);

            @Override
            public void handleError(Throwable t) {
                logger.error("Erro ao processar mensagem: {}", t.getMessage(), t);
            }
        });
        return factory;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(JogadorListener jogadorListener) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(jogadorListener, "processMessage");
        adapter.setMessageConverter(jsonMessageConverter());
        return adapter;
    }
}
