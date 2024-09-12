package com.infnet.conta.client;

import com.infnet.conta.dto.ContaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "conta-service", url = "http://localhost:8088")
public interface ContaClient {
    @GetMapping("/contas/{id}")
    ResponseEntity<ContaDTO> getContaById(@PathVariable("id") Long id);
}
