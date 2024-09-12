package com.infnet.jogo.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infnet.jogo.dto.BibliotecaDTO;
import com.infnet.jogo.service.BibliotecaService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BibliotecaConsumer {

    @Autowired
    private BibliotecaService bibliotecaService;  // Adicione o serviço de biblioteca

    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = "biblioteca_Queue")
    public void listenCompraQueue(String message){
        try {
            // Cria a biblioteca para o usuário, se não existir
            BibliotecaDTO bibliotecaDTO = objectMapper.readValue(message, BibliotecaDTO.class);
            bibliotecaService.adicionarJogo(bibliotecaDTO);
            System.out.println("Biblioteca criada para o usuário com ID: " + bibliotecaDTO.getJogadorId());
        } catch (Exception e) {
            System.err.println("Erro ao processar a criação da biblioteca: " + e.getMessage());
        }
    }
}
