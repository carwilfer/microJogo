package com.infnet.jogo.controller;

import com.infnet.jogo.dto.BibliotecaDTO;
import com.infnet.jogo.dto.JogoDTO;
import com.infnet.jogo.model.Biblioteca;
import com.infnet.jogo.service.BibliotecaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bibliotecas")
public class BibliotecaController {

    private static final Logger log = LoggerFactory.getLogger(BibliotecaController.class);

    @Autowired
    private BibliotecaService bibliotecaService;

    @PostMapping("/adicionar")
    public ResponseEntity<String> adicionarJogo(@RequestBody BibliotecaDTO bibliotecaDTO) {
        try {
            bibliotecaService.adicionarJogo(bibliotecaDTO);
            return ResponseEntity.ok("Jogo adicionado com sucesso");
        } catch (Exception e) {
            log.error("Erro ao adicionar jogo à biblioteca", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao adicionar jogo à biblioteca: " + e.getMessage());
        }
    }

    // Endpoint para listar os jogos de uma biblioteca pertencente a um jogador
    @GetMapping("/{bibliotecaId}/jogos")
    public ResponseEntity<List<JogoDTO>> listarJogos(@PathVariable Long bibliotecaId) {
        List<JogoDTO> jogos = bibliotecaService.listarJogos(bibliotecaId);
        return ResponseEntity.ok(jogos);
    }

    // Endpoint para obter a biblioteca de um jogador
    @GetMapping("/jogador/{jogadorId}")
    public ResponseEntity<Biblioteca> getBiblioteca(@PathVariable Long jogadorId) {
        Biblioteca biblioteca = bibliotecaService.findByJogadorId(jogadorId);
        return ResponseEntity.ok(biblioteca);
    }
}
