package com.infnet.empresa.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infnet.empresa.dto.EmpresaDTO;
import com.infnet.empresa.dto.UsuarioDTO;
import com.infnet.empresa.repository.EmpresaRepository;
import com.infnet.empresa.service.EmpresaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmpresaConsumer {

    @Autowired
    private EmpresaService empresaService;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private static final Logger LOGGER = LoggerFactory.getLogger(EmpresaConsumer.class);

    @RabbitListener(queues = "empresaQueue")
    public void consumirUsuarioCriado(String mensagem) {
        LOGGER.info("Mensagem recebida: [{}]", mensagem);

        try {
            if (mensagem.startsWith("[") && mensagem.endsWith("]")) {
                mensagem = mensagem.substring(1, mensagem.length() - 1);
            }

            EmpresaDTO empresaDTO = objectMapper.readValue(mensagem, EmpresaDTO.class);
            LOGGER.info("Mensagem desserializada com sucesso: [{}]", empresaDTO);

            if (empresaDTO.getCnpj() != null) {
                empresaService.criarEmpresa(empresaDTO);
            }
        } catch (Exception e) {
            LOGGER.error("Erro ao processar mensagem", e);
        }
    }
}
