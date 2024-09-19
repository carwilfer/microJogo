package com.infnet.usuario.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infnet.usuario.configRabbit.RabbitConfig;
import com.infnet.usuario.dto.EmpresaDTO;
import com.infnet.usuario.dto.UsuarioDTO;
import com.infnet.usuario.producer.RabbitMQProducer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class RabbitMQProducerTest {

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitMQProducer rabbitMQProducer;

    @Test
    public void testSendMessage() throws JsonProcessingException {
        UsuarioDTO mockUsuario = new UsuarioDTO(1L, "Carlos", "123456789", null, null, "carlos@example.com", "password", true, "JOGADOR");

        rabbitMQProducer.sendMessage(mockUsuario);

        Mockito.verify(rabbitTemplate).convertAndSend(
                Mockito.eq(RabbitConfig.EXCHANGE_NAME),
                Mockito.eq(RabbitConfig.ROUTING_KEY),
                Mockito.anyString()
        );
    }

    @Test
    public void testSendMessageToEmpresaQueue() throws JsonProcessingException {
        EmpresaDTO mockEmpresa = new EmpresaDTO("Empresa X", "12345678000199");

        rabbitMQProducer.sendMessageToEmpresaQueue(mockEmpresa);

        Mockito.verify(rabbitTemplate).convertAndSend(
                Mockito.eq(RabbitConfig.EMPRESA_EXCHANGE_NAME),
                Mockito.eq(RabbitConfig.EMPRESA_ROUTING_KEY),
                Mockito.anyString()
        );
    }
}
