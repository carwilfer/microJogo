package com.infnet.usuario.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infnet.usuario.dto.UsuarioDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post; // Corrigido para MockMvcRequestBuilders
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCriarUsuarioAdmin() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("carlosW");
        usuarioDTO.setEmail("carlosWiliams@example.com");
        usuarioDTO.setSenha("senha123");
        usuarioDTO.setTipoUsuario("ADMIN");

        mockMvc.perform(post("/api/usuarios/criar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("carlosW"))
                .andExpect(jsonPath("$.email").value("carlosWiliams@example.com"))
                .andExpect(jsonPath("$.tipoUsuario").value("ADMIN"))
                .andExpect(jsonPath("$.cpf").doesNotExist()); // cpf não deve existir para ADMIN
    }

//    @Test
//    public void testCriarUsuarioJogador() throws Exception {
//        UsuarioDTO usuarioDTO = new UsuarioDTO();
//        usuarioDTO.setNome("carlosJ");
//        usuarioDTO.setEmail("carlosJones@example.com");
//        usuarioDTO.setSenha("senha123");
//        usuarioDTO.setTipoUsuario("JOGADOR");
//        usuarioDTO.setCpf("12345678900");
//
//        mockMvc.perform(post("/api/usuarios/criar")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(usuarioDTO)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").exists())
//                .andExpect(jsonPath("$.nome").value("carlosJ"))
//                .andExpect(jsonPath("$.email").value("carlosJones@example.com"))
//                .andExpect(jsonPath("$.tipoUsuario").value("JOGADOR"))
//                .andExpect(jsonPath("$.cpf").value("12345678900")); // cpf deve existir para JOGADOR
//    }
}
