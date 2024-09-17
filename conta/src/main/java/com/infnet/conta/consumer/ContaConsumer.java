package com.infnet.conta.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infnet.conta.dto.CompraDTO;
import com.infnet.conta.dto.ContaDTO;
import com.infnet.conta.dto.UsuarioDTO;
import com.infnet.conta.service.ContaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Component
public class ContaConsumer {

    private static final Logger log = LoggerFactory.getLogger(ContaConsumer.class);

    @Autowired
    private ContaService contaService;

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "conta_Queue")
    public void receiveMessage(String message) {
        try {
            // Deserializando para CompraDTO
            CompraDTO compraDTO = objectMapper.readValue(message, CompraDTO.class);
            Long usuarioId = compraDTO.getUsuarioId();
            System.out.println(message);
            Boolean success = contaService.atualizarSaldo(compraDTO, usuarioId);
            if (!success) {
                log.warn("Saldo insuficiente para a compra: {}", compraDTO);
            }
        } catch (Exception e) {
            log.error("Erro ao processar mensagem de conta: {}", message, e);
        }
    }
}
