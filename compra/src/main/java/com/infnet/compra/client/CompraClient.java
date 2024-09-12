package com.infnet.compra.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "compra-service", url = "http://localhost:8085")
public interface CompraClient {
    @GetMapping("/compras/{id}")
    ResponseEntity<Object> encontrarPorId(@PathVariable("id") Long id);
}
