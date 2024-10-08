package com.infnet.conta.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitConfig {

    public static final String CONTA_QUEUE_NAME = "conta_Queue";
    public static final String CONTA_EXCHANGE_NAME = "contaExchange";
    public static final String CONTA_ROUTING_KEY = "conta.#";

    @Bean
    public Queue queue() {
        return new Queue(CONTA_QUEUE_NAME, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(CONTA_EXCHANGE_NAME);
    }

    @Bean
    public Binding bindingUsuario(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(CONTA_ROUTING_KEY);
    }

    @Bean
    public Queue contaCriarContaQueue() {
        return new Queue("conta_CriarQueue", true);  // true para que a fila seja durável
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
}