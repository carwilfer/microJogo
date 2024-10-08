package com.infnet.avaliacao.controller;

import com.infnet.avaliacao.dto.AvaliacaoDTO;
import com.infnet.avaliacao.model.Avaliacao;
import com.infnet.avaliacao.service.AvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @GetMapping
    public List<Avaliacao> getAllAvaliacoes() {
        return avaliacaoService.getAllAvaliacoes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoDTO> getAvaliacaoById(@PathVariable Long id) {
        Optional<AvaliacaoDTO> avaliacaoDTO = avaliacaoService.getAvaliacaoById(id);
        return avaliacaoDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/listar/avaliacaoJogo/{id}")
    public ResponseEntity<List<Avaliacao>> encontrarAvaliacaoId(@PathVariable Long id) throws Exception {
        List<Avaliacao> avaliacoes = avaliacaoService.encontrarAvaliacaoId(id);
        return ResponseEntity.ok(avaliacoes);
    }

    @PostMapping
    public ResponseEntity<Avaliacao> createAvaliacao(@RequestBody AvaliacaoDTO avaliacaoDTO) {
        Avaliacao novaAvaliacao = avaliacaoService.createAvaliacao(avaliacaoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaAvaliacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Avaliacao> updateAvaliacao(@PathVariable Long id, @RequestBody AvaliacaoDTO avaliacaoDTO) {
        Avaliacao avaliacaoAtualizada = avaliacaoService.updateAvaliacao(id, avaliacaoDTO);
        if (avaliacaoAtualizada != null) {
            return ResponseEntity.ok(avaliacaoAtualizada);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvaliacao(@PathVariable Long id) {
        avaliacaoService.deleteAvaliacao(id);
        return ResponseEntity.noContent().build();
    }
}
