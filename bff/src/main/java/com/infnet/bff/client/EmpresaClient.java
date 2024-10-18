package com.infnet.bff.client;

import com.infnet.bff.dto.EmpresaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "empresa-service",  url = "http://localhost:8085")
@FeignClient(name = "empresa-service",  url = "http://empresa-service:8085")
public interface EmpresaClient {
    @GetMapping("/api/empresas/{id}")
    EmpresaDTO encontrarPorId(@PathVariable("id") Long id);

    @GetMapping("api/empresas/cnpj/{cnpj}")
    EmpresaDTO encontrarPorCnpj(@PathVariable("cnpj") String cnpj);
}
