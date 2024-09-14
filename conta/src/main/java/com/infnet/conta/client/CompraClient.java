package com.infnet.conta.client;

import com.infnet.conta.dto.CompraDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "compra-service", url = "http://localhost:8088")
public interface CompraClient {
    @GetMapping("/compras/{id}")
    ResponseEntity<CompraDTO> getCompraById(@PathVariable("id") Long id);
}
