package com.infnet.jogo.service;

import com.infnet.jogo.controller.BibliotecaController;
import com.infnet.jogo.dto.BibliotecaDTO;
import com.infnet.jogo.dto.JogoDTO;
import com.infnet.jogo.model.Biblioteca;
import com.infnet.jogo.model.Jogo;
import com.infnet.jogo.repository.BibliotecaRepository;
import com.infnet.jogo.repository.JogoRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BibliotecaService {

    private static final Logger log = LoggerFactory.getLogger(BibliotecaService.class);
    @Autowired
    private JogoRepository jogoRepository;

    @Autowired
    private BibliotecaRepository bibliotecaRepository;

    @Transactional
    public Biblioteca adicionarJogo(BibliotecaDTO bibliotecaDTO) {
        try {
            Biblioteca biblioteca = bibliotecaRepository.findByJogadorId(bibliotecaDTO.getUsuarioId())
                    .orElseGet(() -> criarBiblioteca(bibliotecaDTO.getUsuarioId()));

            // Inicializar lista de jogos se for null
            if (biblioteca.getJogos() == null) {
                biblioteca.setJogos(new ArrayList<>());
            }

            // Adicionar o jogo à lista de jogos da biblioteca
            Jogo jogo = jogoRepository.findById(bibliotecaDTO.getJogoId())
                    .orElseThrow(() -> new IllegalArgumentException("Jogo não encontrado para o ID: " + bibliotecaDTO.getJogoId()));

            if (!biblioteca.getJogos().contains(jogo)) {
                biblioteca.getJogos().add(jogo);
            }

            return bibliotecaRepository.save(biblioteca);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao adicionar jogo à biblioteca", e);
        }
    }

    public List<JogoDTO> listarJogos(Long bibliotecaId) {
        // Buscar a biblioteca e retornar os jogos dela
        Biblioteca biblioteca = bibliotecaRepository.findById(bibliotecaId)
                .orElseThrow(() -> new IllegalArgumentException("Biblioteca não encontrada para o ID: " + bibliotecaId));

        return biblioteca.getJogos().stream()
                .map(jogo -> {
                    JogoDTO jogoDTO = new JogoDTO();
                    BeanUtils.copyProperties(jogo, jogoDTO);
                    return jogoDTO;
                })
                .collect(Collectors.toList());
    }

    public Biblioteca findByJogadorId(Long jogadorId) {
        return bibliotecaRepository.findByJogadorId(jogadorId)
                .orElseThrow(() -> new IllegalArgumentException("Biblioteca não encontrada para o jogador ID: " + jogadorId));
    }

    // Método para criar uma nova biblioteca
    public Biblioteca criarBiblioteca(Long usuarioID) {
        Biblioteca novaBiblioteca = new Biblioteca();
        novaBiblioteca.setUsuarioId(usuarioID);
        novaBiblioteca.setJogos(new ArrayList<>());
        return bibliotecaRepository.save(novaBiblioteca);
    }
}
