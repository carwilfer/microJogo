package com.infnet.jogador.configRabbit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infnet.jogador.dto.JogadorDTO;
import com.infnet.jogador.service.JogadorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class JogadorListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(JogadorListener.class);

    private final JogadorService jogadorService;
    private final ObjectMapper objectMapper;

    public JogadorListener(JogadorService jogadorService, ObjectMapper objectMapper) {
        this.jogadorService = jogadorService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "jogadorQueue")
    public void processarMensagem(String message) {
        try {
            LOGGER.info("Mensagem recebida: {}", message);

            JogadorDTO jogadorDTO = objectMapper.readValue(message, JogadorDTO.class);
            LOGGER.info("Mensagem desserializada com sucesso2: {}", jogadorDTO);

            if (jogadorDTO.getCpf() != null) {
                jogadorService.criarJogador(jogadorDTO);
            }
        } catch (JsonProcessingException e) {
            LOGGER.error("Erro ao desserializar a mensagem: {}", e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Erro ao processar a mensagem: {}", e.getMessage(), e);
        }
    }
}