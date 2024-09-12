package com.infnet.jogo.client;

import com.infnet.jogo.dto.JogoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "jogo-service", url = "http://localhost:8085")
public interface JogoClient {
    @GetMapping("/jogos/{id}")
    ResponseEntity<JogoDTO> encontrarPorId(@PathVariable("id") Long jogoId);

    @PostMapping("/jogos/criar")
    ResponseEntity<JogoDTO> criarJogo(@RequestBody JogoDTO jogoDTO);
}