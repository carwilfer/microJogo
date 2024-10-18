package com.infnet.bff.client;

import com.infnet.bff.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "usuario-service", url = "http://localhost:8084/api/usuarios")
@FeignClient(name = "usuario-service", url = "http://usuario-service:8084/api/usuarios")
public interface UsuarioClient {
    @GetMapping("/{id}")
    UsuarioDTO encontrarPorId(@PathVariable("id") Long id);
}
