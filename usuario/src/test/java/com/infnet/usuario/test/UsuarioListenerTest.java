package com.infnet.usuario.test;

import com.infnet.usuario.configRabbit.UsuarioListener;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@SpringBootTest
public class UsuarioListenerTest {

    @Autowired
    private UsuarioListener usuarioListener;

    @Test
    public void testProcessarMensagem() throws UnsupportedEncodingException {
        String mockMessage = "{\"id\":1,\"nome\":\"Carlos\",\"email\":\"carlos@example.com\"}";

        usuarioListener.processarMensagem(mockMessage.getBytes(StandardCharsets.UTF_8));

        // Aqui você pode verificar logs ou o comportamento que espera
        // após o processamento da mensagem, caso tenha alguma lógica adicional.
    }
}
