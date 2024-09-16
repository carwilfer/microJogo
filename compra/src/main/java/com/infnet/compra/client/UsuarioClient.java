package com.infnet.compra.client;

import com.infnet.compra.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "usuario-service", url = "http://localhost:8084")
public interface UsuarioClient {

    @GetMapping("/api/usuarios/{id}")
    UsuarioDTO encontrarPorId(@PathVariable("id") Long id);
}