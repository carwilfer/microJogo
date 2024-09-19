package com.infnet.usuario.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infnet.usuario.client.EmpresaClient;
import com.infnet.usuario.dto.EmpresaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
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

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class EmpresaClientTest {

    @Autowired
    private EmpresaClient empresaClient;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void testGetEmpresaSuccess() throws Exception {
        Long empresaId = 1L;
        EmpresaDTO mockEmpresa = new EmpresaDTO("Empresa X", "12345678000199");

        mockServer.expect(MockRestRequestMatchers.requestTo("http://empresa-service/api/empresas/" + empresaId))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess(new ObjectMapper().writeValueAsString(mockEmpresa), MediaType.APPLICATION_JSON));

        EmpresaDTO empresaDTO = empresaClient.getEmpresa(empresaId);

        assertNotNull(empresaDTO);
        assertEquals("Empresa X", empresaDTO.getRazaoSocial());
        assertEquals("12345678000199", empresaDTO.getCnpj());
    }

    @Test
    public void testGetEmpresaNotFound() {
        Long empresaId = 99L;

        mockServer.expect(MockRestRequestMatchers.requestTo("http://empresa-service/api/empresas/" + empresaId))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.NOT_FOUND));

        EmpresaDTO empresaDTO = empresaClient.getEmpresa(empresaId);

        assertNull(empresaDTO); // Deve retornar null quando a empresa não for encontrada
    }
}
