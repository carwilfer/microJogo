package com.infnet.jogo.service;

import com.infnet.jogo.client.EmpresaClient;
import com.infnet.jogo.dto.EmpresaDTO;
import com.infnet.jogo.dto.JogoDTO;
import com.infnet.jogo.model.Jogo;
import com.infnet.jogo.repository.JogoRepository;
import feign.FeignException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JogoService {

    @Autowired
    private JogoRepository jogoRepository;

    @Autowired
    private EmpresaClient empresaClient;

    @Transactional
    public JogoDTO criarJogo(JogoDTO jogoDTO) {
        try {
            EmpresaDTO empresaDTO = validarEmpresa(jogoDTO.getEmpresaId());
            Jogo jogo = new Jogo();
            BeanUtils.copyProperties(jogoDTO, jogo);

            // Atribuindo CNPJ e nome da empresa ao jogo
            jogo.setCnpj(empresaDTO.getCnpj());
            jogo.setNomeEmpresa(empresaDTO.getRazaoSocial());

            Jogo novoJogo = jogoRepository.save(jogo);
            JogoDTO novoJogoDTO = new JogoDTO();
            BeanUtils.copyProperties(novoJogo, novoJogoDTO);
            return novoJogoDTO;
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados inválidos", e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao criar jogo", e);
        }
    }

    public List<JogoDTO> listarJogos() {
        return jogoRepository.findAll().stream()
                .map(jogo -> {
                    JogoDTO jogoDTO = new JogoDTO();
                    BeanUtils.copyProperties(jogo, jogoDTO);
                    return jogoDTO;
                })
                .collect(Collectors.toList());
    }

    public Optional<JogoDTO> encontrarPorId(Long id) {
        return jogoRepository.findById(id)
                .map(jogo -> {
                    JogoDTO jogoDTO = new JogoDTO();
                    BeanUtils.copyProperties(jogo, jogoDTO);
                    return jogoDTO;
                });
    }

    public List<Jogo>encontrarEmpresasId(Long id) throws Exception {
        List<Jogo> jogoList = jogoRepository.encontrarEmpresasId(id);
        if(!jogoList.isEmpty()){
            return jogoList;
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found");
        }
    }

    public void deletarJogo(Long id) {
        jogoRepository.deleteById(id);
    }

    public Optional<JogoDTO> atualizarJogo(Long id, JogoDTO jogoDTO) {
        EmpresaDTO empresaDTO = validarEmpresa(jogoDTO.getEmpresaId());
        Jogo jogoExistente = jogoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Jogo não encontrado para o ID: " + id));

        // Atualizando as propriedades, incluindo CNPJ e nome da empresa
        BeanUtils.copyProperties(jogoDTO, jogoExistente, "id");
        jogoExistente.setCnpj(empresaDTO.getCnpj());
        jogoExistente.setNomeEmpresa(empresaDTO.getRazaoSocial());

        Jogo jogoAtualizado = jogoRepository.save(jogoExistente);
        JogoDTO jogoAtualizadoDTO = new JogoDTO();
        BeanUtils.copyProperties(jogoAtualizado, jogoAtualizadoDTO);
        return Optional.of(jogoAtualizadoDTO);
    }

    private EmpresaDTO validarEmpresa(Long empresaId) {
        return Optional.ofNullable(empresaClient.encontrarPorId(empresaId))
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada para o ID: " + empresaId));
    }

//    private EmpresaDTO validarEmpresa(Long empresaId) {
//        try {
//            EmpresaDTO empresaDTO = empresaClient.encontrarPorId(empresaId);
//            if (empresaDTO == null) {
//                throw new IllegalArgumentException("Empresa não encontrada para o ID: " + empresaId);
//            }
//            return empresaDTO;
//        } catch (FeignException e) {
//            e.printStackTrace();
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao comunicar com o serviço de empresa", e);
//        }
//    }
}
