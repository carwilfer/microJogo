package com.infnet.empresa.configRabbit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infnet.empresa.dto.EmpresaDTO;
import com.infnet.empresa.dto.UsuarioDTO;
import com.infnet.empresa.model.Empresa;
import com.infnet.empresa.repository.EmpresaRepository;
import com.infnet.empresa.service.EmpresaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmpresaListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmpresaListener.class);

    private final EmpresaService empresaService;
    private final ObjectMapper objectMapper;

    public EmpresaListener(EmpresaService empresaService, ObjectMapper objectMapper) {
        this.empresaService = empresaService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "empresaQueue")
    public void processarMensagem(String message) {
        try {
            LOGGER.info("Mensagem recebida: {}", message);

            EmpresaDTO empresaDTO = objectMapper.readValue(message, EmpresaDTO.class);
            LOGGER.info("Mensagem desserializada com sucesso2: {}", empresaDTO);

            if (empresaDTO.getCnpj() != null) {
                empresaService.criarEmpresa(empresaDTO);
            }
        } catch (JsonProcessingException e) {
            LOGGER.error("Erro ao desserializar a mensagem: {}", e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Erro ao processar a mensagem: {}", e.getMessage(), e);
        }
    }
}
