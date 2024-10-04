package com.infnet.conta.client;

import com.infnet.conta.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "usuario-service", url = "http://localhost:8084/api/usuarios")
@FeignClient(name = "usuario-service", url = "http://usuario-service:8084/api/usuarios")
public interface UsuarioClient {
    @GetMapping("/{id}")
    UsuarioDTO encontrarPorId(@PathVariable("id") Long id);

//    @GetMapping("/cpf/{cpf}")
//    UsuarioDTO encontrarPorCpf(@PathVariable("cpf") String cpf);
//
//    @GetMapping("/cnpj/{cnpj}")
//    UsuarioDTO encontrarPorCnpj(@PathVariable("cnpj") String cnpj);
//
//    @ExceptionHandler(Exception.class)
//    default ResponseEntity<String> handleException(Exception e) {
//        // Manipule e registre o erro conforme necessário
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body("Erro ao processar a solicitação");
//    }
}
