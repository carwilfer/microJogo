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
            CompraDTO compraDTO = objectMapper.readValue(message, CompraDTO.class);
            Long usuarioId = compraDTO.getUsuarioId();
            contaService.atualizarSaldo(compraDTO, usuarioId);
        } catch (JsonProcessingException e) {
            log.error("Erro ao processar mensagem JSON: {}", message, e);
        } catch (Exception e) {
            log.error("Erro ao atualizar saldo", e);
        }
    }
    @RabbitListener(queues = "conta_CriarQueue")
    public void criarConta(String message) {
        try {
            UsuarioDTO usuarioDTO = objectMapper.readValue(message, UsuarioDTO.class);
            Long usuarioId = usuarioDTO.getId();

            // Criando ContaDTO a partir do UsuarioDTO
            ContaDTO contaDTO = new ContaDTO();
            contaDTO.setUsuarioId(usuarioId);
            contaDTO.setAtivo(true); // Ou outro valor padrão
            contaDTO.setLimiteDisponivel(1000.0); // Definir limite disponível, se aplicável

            contaService.createConta(contaDTO); // Chamar o método com ContaDTO
        } catch (JsonProcessingException e) {
            log.error("Erro ao processar mensagem JSON: {}", message, e);
        }
    }
}
