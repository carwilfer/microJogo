package com.infnet.jogo.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infnet.jogo.controller.BibliotecaController;
import com.infnet.jogo.dto.BibliotecaDTO;
import com.infnet.jogo.service.BibliotecaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class BibliotecaConsumer {

    private static final Logger log = LoggerFactory.getLogger(BibliotecaConsumer.class);

    @Autowired
    private BibliotecaService bibliotecaService;  // Adicione o serviço de biblioteca

    @Autowired
    @Qualifier("apiMessageConverter")
    private Jackson2JsonMessageConverter apiMessageConverter;

    private final ObjectMapper objectMapper;

    @Autowired
    public BibliotecaConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "biblioteca_Queue")
    public void listenCompraQueue(String message) {
        try {
            System.out.println("Mensagem recebida: " + message);
            BibliotecaDTO bibliotecaDTO = objectMapper.readValue(message, BibliotecaDTO.class);

            if (bibliotecaDTO.getUsuarioId() == null) {
                System.err.println("Erro: ID do usuário é nulo na mensagem recebida");
                return;
            }

            // Criar a bibliotecaDTO com dados ajustados para adicionar jogos
            BibliotecaDTO novaBibliotecaDTO = new BibliotecaDTO();
            novaBibliotecaDTO.setUsuarioId(bibliotecaDTO.getUsuarioId());
            novaBibliotecaDTO.setJogoId(bibliotecaDTO.getJogoId());

            bibliotecaService.adicionarJogo(novaBibliotecaDTO);
            System.out.println("Biblioteca atualizada para o usuário com ID: " + bibliotecaDTO.getUsuarioId());
        } catch (Exception e) {
            System.err.println("Erro ao processar a criação da biblioteca: " + e.getMessage());
        }
    }
}
