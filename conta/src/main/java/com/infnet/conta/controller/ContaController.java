package com.infnet.conta.controller;

import com.infnet.conta.dto.CompraDTO;
import com.infnet.conta.dto.ContaDTO;
import com.infnet.conta.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @PostMapping
    public ResponseEntity<ContaDTO> createConta(@RequestBody ContaDTO contaDTO) {
        ContaDTO createdConta = contaService.createConta(contaDTO);
        return ResponseEntity.ok(createdConta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaDTO> getContaById(@PathVariable Long id) {
        ContaDTO contaDTO = contaService.getContaById(id);
        if (contaDTO != null) {
            return ResponseEntity.ok(contaDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/compra")
    public ResponseEntity<Void> processarCompra(@RequestBody CompraDTO compraDTO) {
        Boolean success = contaService.atualizarSaldo(compraDTO);
        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
