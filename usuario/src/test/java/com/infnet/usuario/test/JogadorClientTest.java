package com.infnet.usuario.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infnet.usuario.client.JogadorClient;
import com.infnet.usuario.dto.JogadorDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class JogadorClientTest {

    @Autowired
    private JogadorClient jogadorClient;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void testGetJogadorSuccess() throws Exception {
        Long jogadorId = 1L;
        JogadorDTO mockJogador = new JogadorDTO("12345678900");

        mockServer.expect(MockRestRequestMatchers.requestTo("http://jogador-service/api/jogadores/" + jogadorId))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess(new ObjectMapper().writeValueAsString(mockJogador), MediaType.APPLICATION_JSON));

        JogadorDTO jogadorDTO = jogadorClient.getJogador(jogadorId);

        assertNotNull(jogadorDTO);
        assertEquals("12345678900", jogadorDTO.getCpf());
    }

    @Test
    public void testGetJogadorNotFound() {
        Long jogadorId = 99L;

        mockServer.expect(MockRestRequestMatchers.requestTo("http://jogador-service/api/jogadores/" + jogadorId))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.NOT_FOUND));

        JogadorDTO jogadorDTO = jogadorClient.getJogador(jogadorId);

        assertNull(jogadorDTO); // Deve retornar null quando o jogador não for encontrado
    }
}
