package com.infnet.usuario.client;

import com.infnet.usuario.dto.EmpresaDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmpresaClient {

    private final RestTemplate restTemplate;

    @Autowired
    public EmpresaClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public EmpresaDTO getEmpresa(Long id) {
        try {
            return restTemplate.getForObject("http://empresa-service/api/empresas/" + id, EmpresaDTO.class);
        } catch (Exception e) {
            // Log error and handle exception
            Logger logger = LoggerFactory.getLogger(EmpresaClient.class);
            logger.error("Erro ao buscar empresa: {}", e.getMessage(), e);
            return null;
        }
    }
}
