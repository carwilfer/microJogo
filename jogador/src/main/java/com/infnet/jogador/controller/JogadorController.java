package com.infnet.jogador.controller;

import com.infnet.jogador.dto.JogadorDTO;
import com.infnet.jogador.service.JogadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jogadores")
public class JogadorController {

    private final JogadorService jogadorService;

    @Autowired
    public JogadorController(JogadorService jogadorService) {
        this.jogadorService = jogadorService;
    }

    @PostMapping("/criar")
    public ResponseEntity<JogadorDTO> criarJogador(@RequestBody JogadorDTO jogadorDTO) {
        JogadorDTO novoJogador = jogadorService.criarJogador(jogadorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoJogador);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<JogadorDTO>> listarJogadores() {
        List<JogadorDTO> jogadores = jogadorService.listarJogadores();
        return ResponseEntity.ok(jogadores);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<JogadorDTO> obterJogadorPorCpf(@PathVariable String cpf) {
        return jogadorService.encontrarPorCpf(cpf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JogadorDTO> obterJogador(@PathVariable Long id) {
        return jogadorService.encontrarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<JogadorDTO> atualizarJogador(@PathVariable Long id, @RequestBody JogadorDTO jogadorDTO) {
        JogadorDTO jogadorAtualizado = jogadorService.atualizarJogador(id, jogadorDTO);
        return ResponseEntity.ok(jogadorAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarJogador(@PathVariable Long id) {
        jogadorService.deletarJogador(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/ativar")
    public ResponseEntity<Void> ativarJogador(@PathVariable Long id) {
        jogadorService.ativarJogador(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/desativar")
    public ResponseEntity<Void> desativarJogador(@PathVariable Long id) {
        jogadorService.desativarJogador(id);
        return ResponseEntity.noContent().build();
    }
}
