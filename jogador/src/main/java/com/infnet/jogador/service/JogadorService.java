package com.infnet.jogador.service;

import com.infnet.jogador.dto.JogadorDTO;
import com.infnet.jogador.model.Jogador;
import com.infnet.jogador.repository.JogadorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JogadorService {

    private final JogadorRepository jogadorRepository;

    @Autowired
    public JogadorService(JogadorRepository jogadorRepository) {
        this.jogadorRepository = jogadorRepository;
    }

    public JogadorDTO criarJogador(JogadorDTO jogadorDTO) {
        Jogador jogador = new Jogador();
        jogador.setUsuarioId(jogadorDTO.getId());
        BeanUtils.copyProperties(jogadorDTO, jogador,"id");
        Jogador jogadorSalvo = jogadorRepository.save(jogador);
        return new JogadorDTO(jogadorSalvo);
    }

    public JogadorDTO criarJogadorSemUserId(JogadorDTO jogadorDTO) {
        Jogador jogador = new Jogador();

        // Configurar o Jogador com ou sem usuarioId
        jogador.setUsuarioId(jogadorDTO.getId() != null ? jogadorDTO.getId() : null);
        jogador.setNome(jogadorDTO.getNome());
        jogador.setEmail(jogadorDTO.getEmail());
        jogador.setSenha(jogadorDTO.getSenha());
        jogador.setAtivo(jogadorDTO.isAtivo());
        jogador.setCpf(jogadorDTO.getCpf());

        Jogador jogadorSalvo = jogadorRepository.save(jogador);
        return new JogadorDTO(jogadorSalvo);
    }

    public Optional<JogadorDTO> encontrarPorId(Long id) {
        return jogadorRepository.findByUsuarioId(id)
                .map(JogadorDTO::new);
    }

    public Optional<JogadorDTO> encontrarPorCpf(String cpf) {
        String cpfNormalized = cpf.replaceAll("[^0-9]", "");
        return jogadorRepository.findByCpf(cpf)
                .map(JogadorDTO::new);
    }

    public List<JogadorDTO> listarJogadores() {
        List<Jogador> jogadores = jogadorRepository.findAll();
        return jogadores.stream()
                .map(JogadorDTO::new)
                .toList();
    }

    public JogadorDTO atualizarJogador(Long id, JogadorDTO jogadorDTO) {
        Jogador jogador = jogadorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Jogador não encontrado"));

        jogador.setNome(jogadorDTO.getNome());
        jogador.setEmail(jogadorDTO.getEmail());
        jogador.setSenha(jogadorDTO.getSenha());
        jogador.setCpf(jogadorDTO.getCpf());
        jogador.setAtivo(jogadorDTO.isAtivo());

        jogador = jogadorRepository.save(jogador);
        return new JogadorDTO(jogador);
    }

    public void deletarJogador(Long id) {
        jogadorRepository.deleteById(id);
    }

    public void ativarJogador(Long id) {
        Jogador jogador = jogadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jogador não encontrado"));
        jogador.setAtivo(true);
        jogadorRepository.save(jogador);
    }

    public void desativarJogador(Long id) {
        Jogador jogador = jogadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jogador não encontrado"));
        jogador.setAtivo(false);
        jogadorRepository.save(jogador);
    }
}
