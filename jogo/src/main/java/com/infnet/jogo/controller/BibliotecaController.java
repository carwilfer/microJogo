package com.infnet.jogo.controller;

import com.infnet.jogo.dto.JogoDTO;
import com.infnet.jogo.service.BibliotecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bibliotecas")
public class BibliotecaController {

    @Autowired
    private BibliotecaService bibliotecaService;

    // Endpoint para listar os jogos de uma biblioteca
    @GetMapping("/{jogadorId}")
    public ResponseEntity<List<JogoDTO>> listarJogos(@PathVariable Long bibliotecaId) {
        List<JogoDTO> jogos = bibliotecaService.listarJogos(bibliotecaId);
        return ResponseEntity.ok(jogos);
    }
}
