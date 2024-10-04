package com.infnet.avaliacao.service;

import com.infnet.avaliacao.client.JogoFeignClient;
import com.infnet.avaliacao.dto.AvaliacaoDTO;
import com.infnet.avaliacao.model.Avaliacao;
import com.infnet.avaliacao.repository.AvaliacaoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private JogoFeignClient jogoFeignClient;

    public List<Avaliacao> getAllAvaliacoes() {
        return avaliacaoRepository.findAll();
    }

    public Optional<AvaliacaoDTO> getAvaliacaoById(Long id) {
        return avaliacaoRepository.findById(id)
                .map(avaliacao -> {
                    AvaliacaoDTO avaliacaoDTO = new AvaliacaoDTO();
                    BeanUtils.copyProperties(avaliacao, avaliacaoDTO);
                    return avaliacaoDTO;
                });
    }

    public List<Avaliacao>encontrarAvaliacaoId(Long id) throws Exception{
        List<Avaliacao> avaliacaoList = avaliacaoRepository.encontrarAvaliacaoId(id);
        if(!avaliacaoList.isEmpty()){
            return avaliacaoList;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found");
        }

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
