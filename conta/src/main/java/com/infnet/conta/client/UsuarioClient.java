package com.infnet.conta.client;

import com.infnet.conta.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "usuario-service", url = "http://localhost:8084")
public interface UsuarioClient {
    @GetMapping("api/usuarios/{id}")
    UsuarioDTO encontrarPorId(@PathVariable("id") Long id);
}
