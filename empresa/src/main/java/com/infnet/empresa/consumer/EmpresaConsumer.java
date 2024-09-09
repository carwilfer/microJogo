package com.infnet.empresa.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infnet.empresa.configRabbit.RabbitConfig;
import com.infnet.empresa.dto.UsuarioDTO;
import com.infnet.empresa.model.Empresa;
import com.infnet.empresa.repository.EmpresaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpresaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmpresaConsumer.class);

    private final EmpresaRepository empresaRepository;
    private final ObjectMapper objectMapper;

    public EmpresaConsumer(EmpresaRepository empresaRepository, ObjectMapper objectMapper) {
        this.empresaRepository = empresaRepository;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void consumirUsuarioCriado(String mensagem) {
        try {
            // Converte a mensagem JSON para o objeto UsuarioDTO
            UsuarioDTO usuarioDTO = objectMapper.readValue(mensagem, UsuarioDTO.class);

            if (usuarioDTO.getCnpj() != null) {
                Empresa empresa = new Empresa();
                empresa.setNome(usuarioDTO.getNome());
                empresa.setEmail(usuarioDTO.getEmail());
                empresa.setSenha(usuarioDTO.getSenha());
                empresa.setAtivo(usuarioDTO.getAtivo());
                empresa.setCnpj(usuarioDTO.getCnpj());
                empresa.setRazaoSocial(usuarioDTO.getRazaoSocial());

                empresaRepository.save(empresa);
            }
        } catch (Exception e) {
            // Registrar o erro e tratar a exceção
            e.printStackTrace();
        }
        LOGGER.info("Empresa recebida com sucesso: [{}]", mensagem);
    }
}
