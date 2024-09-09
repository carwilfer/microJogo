package com.infnet.usuario.configRabbit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infnet.usuario.dto.EmpresaDTO;
import com.infnet.usuario.dto.JogadorDTO;
import com.infnet.usuario.dto.UsuarioDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UsuarioListener {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioListener.class);

    public void processarMensagem(byte[] message) {
        try {
            String messageStr = new String(message, "UTF-8");
            LOGGER.info("Mensagem recebida: {}", messageStr);

            // Detecta e desserializa o tipo correto de DTO
            UsuarioDTO usuarioDTO;
            try {
                usuarioDTO = objectMapper.readValue(messageStr, UsuarioDTO.class);
            } catch (JsonProcessingException e) {
                // Se falhar ao desserializar como UsuarioDTO, tente como EmpresaDTO ou JogadorDTO
                try {
                    usuarioDTO = objectMapper.readValue(messageStr, EmpresaDTO.class);
                } catch (JsonProcessingException ex) {
                    usuarioDTO = objectMapper.readValue(messageStr, JogadorDTO.class);
                }
            }

            // Processar usuarioDTO
            // ...

        } catch (Exception e) {
            LOGGER.error("Erro ao processar mensagem: {}", e.getMessage(), e);
        }
    }
}
