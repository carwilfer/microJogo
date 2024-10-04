package com.infnet.compra.client;

import com.infnet.compra.dto.JogoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "jogo-service", url = "http://localhost:8087")
@FeignClient(name = "jogo-service", url = "http://jogo-service:8087")
public interface JogoClient {
    @GetMapping("/jogos/{id}")
    JogoDTO encontrarPorId(@PathVariable("id") Long id);
}
