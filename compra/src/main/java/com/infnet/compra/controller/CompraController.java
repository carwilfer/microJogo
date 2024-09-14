package com.infnet.compra.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infnet.compra.model.Compra;
import com.infnet.compra.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/compras")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @PostMapping("/criar")
    public ResponseEntity<Compra> criarCompra(@RequestBody Compra compra) {
        try {
            Compra novaCompra = compraService.criarCompra(compra);
            return ResponseEntity.ok(novaCompra);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/listar")
    public List<Compra> listarCompras() {
        return compraService.listarCompras();
    }

    @GetMapping("/listar/{usuario_id}")
    public ResponseEntity<List<Compra>> listarComprasPorUsuario(@PathVariable Long usuarioId) {
        List<Compra> compras = compraService.encontrarPorUsuarioId(usuarioId);
        return ResponseEntity.ok(compras);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compra> encontrarPorId(@PathVariable Long id) {
        Optional<Compra> compra = compraService.encontrarPorId(id);
        return compra.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
