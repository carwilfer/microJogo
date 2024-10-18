package com.infnet.bff.client;

import com.infnet.bff.dto.CompraDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

//@FeignClient(name = "compra-service", url = "http://localhost:8088")
@FeignClient(name = "compra-service", url = "http://compra-service:8088")
public interface CompraClient {
    @GetMapping("/compras/{id}")
    ResponseEntity<CompraDTO> getCompraById(@PathVariable("id") Long id);

    @PostMapping("/api/compras/criar")
    CompraDTO criarCompra(@RequestBody CompraDTO compraDTO);

    // Adiciona o método para buscar compras por usuarioId
    @GetMapping("/compras/usuario/{usuarioId}")
    List<CompraDTO> buscarComprasPorUsuarioId(@PathVariable("usuarioId") Long usuarioId);
}
