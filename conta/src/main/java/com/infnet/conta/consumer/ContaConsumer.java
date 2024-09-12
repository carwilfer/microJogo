package com.infnet.conta.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infnet.conta.dto.CompraDTO;
import com.infnet.conta.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Component
public class ContaConsumer {

    @Autowired
    private ContaService contaService;

    @Autowired
    ObjectMapper objectMapper;

    @RabbitListener(queues = "compra_queue")
    public void receiveMessage(String message) throws JsonProcessingException {
        CompraDTO compraDTO = objectMapper.readValue(message, CompraDTO.class);
        contaService.atualizarSaldo(compraDTO);
    }
}
