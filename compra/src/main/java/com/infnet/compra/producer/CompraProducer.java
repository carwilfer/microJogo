package com.infnet.compra.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infnet.compra.config.RabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompraProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompraProducer.class);

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public CompraProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessageToContaQueue(Object message) throws JsonProcessingException {
        String messageJson = objectMapper.writeValueAsString(message);
        LOGGER.info("Enviando mensagem para ContaQueue [{}]", messageJson);
        rabbitTemplate.convertAndSend(RabbitConfig.CONTA_EXCHANGE_NAME, RabbitConfig.CONTA_ROUTING_KEY, messageJson);
    }

    public void sendMessageToBibliotecaQueue(Object message) throws JsonProcessingException {
        String messageJson = objectMapper.writeValueAsString(message);
        LOGGER.info("Enviando mensagem para BobliotecaQueue [{}]", messageJson);
        rabbitTemplate.convertAndSend(RabbitConfig.BIBLIOTECA_EXCHANGE_NAME, RabbitConfig.BIBLIOTECA_ROUTING_KEY, messageJson);
    }
}
