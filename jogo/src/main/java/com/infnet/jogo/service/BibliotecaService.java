package com.infnet.jogo.service;

import com.infnet.jogo.dto.BibliotecaDTO;
import com.infnet.jogo.dto.JogoDTO;
import com.infnet.jogo.model.Biblioteca;
import com.infnet.jogo.model.Jogo;
import com.infnet.jogo.repository.BibliotecaRepository;
import com.infnet.jogo.repository.JogoRepository;
import jakarta.transaction.Transactional;
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

    @Autowired
    private JogoRepository jogoRepository;

    @Autowired
    private BibliotecaRepository bibliotecaRepository;

    @Transactional
    public Biblioteca adicionarJogo(BibliotecaDTO bibliotecaDTO) {
        try {
           Biblioteca biblioteca = new Biblioteca();
           Optional<Biblioteca> bibliotecaOpt = bibliotecaRepository.findByJogadorId(bibliotecaDTO.getJogadorId());
           biblioteca = bibliotecaOpt.orElseGet(() -> criarBiblioteca((bibliotecaDTO.getJogadorId())));

            Jogo jogo = jogoRepository.findById(bibliotecaDTO.getJogos())
                    .orElseThrow(() -> new IllegalArgumentException("Jogo não encontrado para o ID: " + bibliotecaDTO.getJogos()));

            biblioteca.getJogos().add(jogo);

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

    // Método para criar uma nova biblioteca
    public Biblioteca criarBiblioteca(Long jogadorId) {
        Biblioteca novaBiblioteca = new Biblioteca();
        novaBiblioteca.setJogadorId(jogadorId);
        novaBiblioteca.setJogos(new ArrayList<>()); // Inicia com uma lista vazia de jogos
        return bibliotecaRepository.save(novaBiblioteca); // Salva a nova biblioteca no banco de dados
    }
}
