package com.infnet.jogo.controller;

import com.infnet.jogo.dto.JogoDTO;
import com.infnet.jogo.service.JogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jogos")
@CrossOrigin(origins = "http://localhost:3000")
public class JogoController {

    @Autowired
    private JogoService jogoService;

    @PostMapping("/criar")
    public ResponseEntity<JogoDTO> criarJogo(@RequestBody JogoDTO jogoDTO) {
        try {
            JogoDTO novoJogo = jogoService.criarJogo(jogoDTO);
            return ResponseEntity.ok(novoJogo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<JogoDTO>> listarJogos() {
        List<JogoDTO> jogos = jogoService.listarJogos();
        return ResponseEntity.ok(jogos);
    }

    @GetMapping("/listar/jogosEmpresa/{id}")
    public ResponseEntity<List<JogoDTO>> encontrarEmpresasId(@PathVariable Long id) throws Exception {
        List<JogoDTO> jogos = jogoService.encontrarEmpresasId(id);
        return ResponseEntity.ok(jogos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JogoDTO> encontrarPorId(@PathVariable Long id) {
        Optional<JogoDTO> jogoDTO = jogoService.encontrarPorId(id);
        return jogoDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarJogo(@PathVariable Long id) {
        jogoService.deletarJogo(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<JogoDTO> atualizarJogo(@PathVariable Long id, @RequestBody JogoDTO jogoDTO) {
        Optional<JogoDTO> jogoAtualizado = jogoService.atualizarJogo(id, jogoDTO);
        return jogoAtualizado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
