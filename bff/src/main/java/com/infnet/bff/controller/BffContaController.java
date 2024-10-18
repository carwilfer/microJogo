package com.infnet.bff.controller;

import com.infnet.bff.dto.ContaDTO;
import com.infnet.bff.service.BffContaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bff/conta")
public class BffContaController {

    @Autowired
    BffContaService bffContaService;

    @PostMapping()
    public ResponseEntity<Object> criarConta (@RequestBody @Valid ContaDTO contaDTO) {
        ContaDTO createdConta = bffContaService.criarConta(contaDTO);
        return ResponseEntity.ok(createdConta);
    }
}
