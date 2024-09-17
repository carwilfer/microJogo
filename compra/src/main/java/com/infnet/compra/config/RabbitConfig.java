package com.infnet.compra.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitConfig {

    public static final String BIBLIOTECA_QUEUE_NAME = "biblioteca_Queue";
    public static final String BIBLIOTECA_EXCHANGE_NAME = "bibliotecaExchange";
    public static final String BIBLIOTECA_ROUTING_KEY = "biblioteca.#";

    public static final String CONTA_QUEUE_NAME = "conta_Queue";
    public static final String CONTA_EXCHANGE_NAME = "contaExchange";
    public static final String CONTA_ROUTING_KEY = "conta.#";

    //Para a fila da BIBLIOTECA que está em jogos
    @Bean
    public Queue bibliotecaQueue() {
        return new Queue(BIBLIOTECA_QUEUE_NAME, true);
    }

    //Para a fila da CONTA
    @Bean
    public Queue contaQueue() {
        return new Queue(CONTA_QUEUE_NAME, true);
    }

    //Para a BIBLIOTECA
    @Bean
    public TopicExchange bibliotecaExchange() {
        return new TopicExchange(BIBLIOTECA_EXCHANGE_NAME);
    }

    //Para a CONTA
    @Bean
    public TopicExchange contaExchange() {
        return new TopicExchange(CONTA_EXCHANGE_NAME);
    }

    //Para a BIBLIOTECA
    @Bean
    public Binding bibliotecaBindingUsuario(Queue bibliotecaQueue, TopicExchange bibliotecaExchange) {
        return BindingBuilder.bind(bibliotecaQueue).to(bibliotecaExchange).with(BIBLIOTECA_ROUTING_KEY);
    }

    //Para a CONTA
    @Bean
    public Binding contaBindingUsuario(Queue contaQueue, TopicExchange contaExchange) {
        return BindingBuilder.bind(contaQueue).to(contaExchange).with(CONTA_ROUTING_KEY);
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
