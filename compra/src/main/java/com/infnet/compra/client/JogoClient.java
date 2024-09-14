package com.infnet.compra.client;

import com.infnet.compra.dto.CompraDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "jogo-service", url = "http://localhost:8087")
public interface JogoClient {
    @GetMapping("/jogos/{id}")
    ResponseEntity<CompraDTO> encontrarPorId(@PathVariable("id") Long id);
}
