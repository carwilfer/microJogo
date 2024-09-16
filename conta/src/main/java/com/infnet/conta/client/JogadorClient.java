package com.infnet.conta.client;

import com.infnet.conta.dto.JogadorDTO;
import feign.FeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "jogador-service", url = "http://localhost:8086")
public interface JogadorClient {
    @GetMapping("api/jogadores/{id}")
    JogadorDTO encontrarPorId(@PathVariable("id") Long id);

    @GetMapping("/api/jogadores/cpf/{cpf}")
    JogadorDTO encontrarPorCpf(@PathVariable("cpf") String cpf);

    @ExceptionHandler(FeignException.class)
    default void handleFeignException(FeignException e) {
        // Log e trate exceções Feign aqui
        System.out.println("Erro ao chamar o serviço de jogador: " + e.getMessage());
    }
}