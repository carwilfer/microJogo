package com.infnet.empresa.controller;

import com.infnet.empresa.dto.EmpresaDTO;
import com.infnet.empresa.service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {

    private final EmpresaService empresaService;
    private final RabbitTemplate rabbitTemplate;

    public EmpresaController(EmpresaService empresaService, RabbitTemplate rabbitTemplate) {
        this.empresaService = empresaService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("criar")
    public ResponseEntity<EmpresaDTO> criarEmpresa(@RequestBody EmpresaDTO empresaDTO) {
        EmpresaDTO novaEmpresa = empresaService.criarEmpresa(empresaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaEmpresa);
    }

    @GetMapping("listar")
    public ResponseEntity<List<EmpresaDTO>> listarEmpresas() {
        List<EmpresaDTO> empresas = empresaService.listarTodas();
        //rabbitTemplate.convertAndSend(EmpresaConsumer.EXCHANGE_NAME, "empresa.listed", empresas);
        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaDTO> obterEmpresa(@PathVariable Long id) {
        return empresaService.encontrarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cnpj/{cnpj}")
    public ResponseEntity<EmpresaDTO> obterEmpresaPorCnpj(@PathVariable String cnpj) {
        return empresaService.encontrarPorCnpj(cnpj)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpresaDTO> atualizarEmpresa(@PathVariable Long id, @RequestBody EmpresaDTO empresaDTO) {
        EmpresaDTO empresaAtualizada = empresaService.atualizarEmpresa(id, empresaDTO);
        return ResponseEntity.ok(empresaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEmpresa(@PathVariable Long id) {
        empresaService.deletarEmpresa(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/ativar")
    public ResponseEntity<Void> ativarEmpresa(@PathVariable Long id) {
        empresaService.ativarEmpresa(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/desativar")
    public ResponseEntity<Void> desativarEmpresa(@PathVariable Long id) {
        empresaService.desativarEmpresa(id);
        return ResponseEntity.noContent().build();
    }
}
