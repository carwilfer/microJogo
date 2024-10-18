package com.infnet.bff.client;

import com.infnet.bff.dto.ContaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@FeignClient(name = "conta-service", url = "http://localhost:8089")
@FeignClient(name = "conta-service", url = "http://conta-service:8089")
public interface ContaClient {
    @GetMapping("/api/contas/user/{id}")
    ContaDTO encontrarPorId(@PathVariable("id") Long id);

    @PostMapping("/api/contas/criar")
    ContaDTO criarConta(@RequestBody ContaDTO conta);
}
