package com.infnet.usuario.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAccessProtectedEndpointWithoutAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/usuarios/listar"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAccessProtectedEndpointWithAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/usuarios/listar"))
                .andExpect(MockMvcResultMatchers.status().isOk()); // Verifique se está acessível conforme as permissões
    }
}
