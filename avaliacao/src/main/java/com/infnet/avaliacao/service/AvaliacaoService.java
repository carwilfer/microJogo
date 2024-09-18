package com.infnet.avaliacao.service;

import com.infnet.avaliacao.client.JogoFeignClient;
import com.infnet.avaliacao.dto.AvaliacaoDTO;
import com.infnet.avaliacao.model.Avaliacao;
import com.infnet.avaliacao.repository.AvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private JogoFeignClient jogoFeignClient;

    public List<Avaliacao> getAllAvaliacoes() {
        return avaliacaoRepository.findAll();
    }

    public Avaliacao getAvaliacaoById(Long id) {
        return avaliacaoRepository.findById(id).orElse(null);
    }

    public Avaliacao createAvaliacao(AvaliacaoDTO avaliacaoDTO) {
        jogoFeignClient.encontrarPorId(avaliacaoDTO.getJogoId());
        // Aqui você pode validar ou usar os dados do jogo se necessário

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setJogoId(avaliacaoDTO.getJogoId());
        avaliacao.setUsuarioId(avaliacaoDTO.getUsuarioId());
        avaliacao.setNota(avaliacaoDTO.getNota());
        avaliacao.setComentario(avaliacaoDTO.getComentario());
        return avaliacaoRepository.save(avaliacao);
    }

    public Avaliacao updateAvaliacao(Long id, AvaliacaoDTO avaliacaoDTO) {
        Avaliacao avaliacao = avaliacaoRepository.findById(id).orElse(null);
        if (avaliacao != null) {
            avaliacao.setJogoId(avaliacaoDTO.getJogoId());
            avaliacao.setUsuarioId(avaliacaoDTO.getUsuarioId());
            avaliacao.setNota(avaliacaoDTO.getNota());
            avaliacao.setComentario(avaliacaoDTO.getComentario());
            return avaliacaoRepository.save(avaliacao);
        }
        return null;
    }

    public void deleteAvaliacao(Long id) {
        avaliacaoRepository.deleteById(id);
    }
}
