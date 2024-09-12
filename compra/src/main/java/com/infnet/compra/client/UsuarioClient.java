package com.infnet.compra.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "usuario-service", url = "http://localhost:8082")
public interface UsuarioClient {
    @GetMapping("/usuarios/{id}")
    ResponseEntity<Object> encontrarPorId(@PathVariable("id") Long id);
}
