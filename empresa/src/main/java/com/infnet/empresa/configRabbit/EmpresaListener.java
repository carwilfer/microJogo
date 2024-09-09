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
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public class EmpresaListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmpresaListener.class);

    private final EmpresaRepository empresaRepository;
    private final ObjectMapper objectMapper;

    public EmpresaListener(EmpresaRepository empresaRepository, ObjectMapper objectMapper) {
        this.empresaRepository = empresaRepository;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void processarMensagem(byte[] message) {
        try {
            String messageStr = new String(message, "UTF-8");
            LOGGER.info("Mensagem recebida: {}", messageStr);
            EmpresaDTO empresaDTO = objectMapper.readValue(messageStr, EmpresaDTO.class);

            Empresa empresa = new Empresa();
            empresa.setNome(empresaDTO.getNome());
            empresa.setEmail(empresaDTO.getEmail());
            empresa.setSenha(empresaDTO.getSenha());
            empresa.setAtivo(empresaDTO.getAtivo());
            empresa.setCnpj(empresaDTO.getCnpj());
            empresa.setRazaoSocial(empresaDTO.getRazaoSocial());

            empresaRepository.save(empresa);

            LOGGER.info("Empresa Criada com sucesso: {}", empresa);

        } catch (Exception e) {
            LOGGER.error("Erro ao processar mensagem: {}", e.getMessage(), e);
        }
    }
}
