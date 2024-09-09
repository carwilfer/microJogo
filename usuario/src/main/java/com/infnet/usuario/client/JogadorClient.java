package com.infnet.usuario.client;

import com.infnet.usuario.dto.JogadorDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class JogadorClient {

    private final RestTemplate restTemplate;

    @Autowired
    public JogadorClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public JogadorDTO getJogador(Long id) {
        try {
            return restTemplate.getForObject("http://jogador-service/api/jogadores/" + id, JogadorDTO.class);
        } catch (Exception e) {
            // Log error and handle exception
            Logger logger = LoggerFactory.getLogger(JogadorClient.class);
            logger.error("Erro ao buscar jogador: {}", e.getMessage(), e);
            return null;
        }
    }
}
