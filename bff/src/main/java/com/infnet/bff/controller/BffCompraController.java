package com.infnet.bff.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infnet.bff.dto.CompraDTO;
import com.infnet.bff.service.BffCompraService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/bff/compra")
public class BffCompraController {

    @Autowired
    BffCompraService bffCompraService;

    @PostMapping()
    public ResponseEntity<Object> criarCompra (@RequestBody @Valid CompraDTO compraDTO) {
        try {
            CompraDTO novaCompra = bffCompraService.criarCompra(compraDTO);
            return ResponseEntity.ok(novaCompra);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
