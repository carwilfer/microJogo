package com.infnet.compra.client;

import com.infnet.compra.dto.CompraDTO;
import com.infnet.compra.dto.ContaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "conta-service", url = "http://localhost:8089")
public interface ContaClient {
    @GetMapping("/api/contas/user/{id}")
    ContaDTO encontrarPorId(@PathVariable("id") Long id);
}
