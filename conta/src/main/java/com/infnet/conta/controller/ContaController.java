package com.infnet.conta.controller;

import com.infnet.conta.client.UsuarioClient;
import com.infnet.conta.dto.CompraDTO;
import com.infnet.conta.dto.ContaDTO;
import com.infnet.conta.dto.UsuarioDTO;
import com.infnet.conta.model.Conta;
import com.infnet.conta.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @PostMapping("/criar")
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

    @GetMapping("/user/{id}")
    public ResponseEntity<ContaDTO> getContaByUserId(@PathVariable Long id) {
        ContaDTO contaDTO = contaService.encontrarPorUsuarioId(id);
        if (contaDTO != null) {
            return ResponseEntity.ok(contaDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/compra")
    public ResponseEntity<Void> processarCompra(@RequestBody CompraDTO compraDTO, @RequestParam Long usuarioId) {
        Boolean success = contaService.atualizarSaldo(compraDTO, usuarioId);
        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping("/admin/atualizar-saldo")
    public ResponseEntity<Void> atualizarSaldoPorAdmin(@RequestParam Long contaId, @RequestParam double valor) {
        Boolean success = contaService.atualizarSaldoPorAdmin(contaId, valor);
        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
