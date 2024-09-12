package com.infnet.compra.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "jogo-service", url = "http://localhost:8081")
public interface JogoClient {
    @GetMapping("/jogos/{id}")
    ResponseEntity<Object> encontrarPorId(@PathVariable("id") Long id);
}
