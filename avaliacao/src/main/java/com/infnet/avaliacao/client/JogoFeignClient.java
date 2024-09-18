package com.infnet.avaliacao.client;

import com.infnet.avaliacao.dto.JogoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "jogo-service", url = "http://localhost:8087")
public interface JogoFeignClient {
    @GetMapping("/jogos/{id}")
    JogoDTO encontrarPorId(@PathVariable("id") Long id);
}
