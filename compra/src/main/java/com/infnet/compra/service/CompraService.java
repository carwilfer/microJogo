package com.infnet.compra.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infnet.compra.client.ContaClient;
import com.infnet.compra.client.JogoClient;
import com.infnet.compra.client.UsuarioClient;
import com.infnet.compra.dto.CompraDTO;
import com.infnet.compra.dto.ContaDTO;
import com.infnet.compra.dto.JogoDTO;
import com.infnet.compra.dto.UsuarioDTO;
import com.infnet.compra.model.Compra;
import com.infnet.compra.producer.CompraProducer;
import com.infnet.compra.repository.CompraRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private CompraProducer compraProducer;

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private JogoClient jogoClient;
    @Autowired
    private ContaClient contaClient;

    public Compra criarCompra(CompraDTO compraDTO) throws JsonProcessingException {
        // Verifique o usuário e o saldo
        UsuarioDTO usuario = usuarioClient.encontrarPorId(compraDTO.getUsuarioId());
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        ContaDTO contaDTO = contaClient.encontrarPorId(compraDTO.getUsuarioId());
        if (contaDTO == null) {
            throw new IllegalArgumentException("Jogo não encontrado");
        }

        // Verifique o jogo
        JogoDTO jogoDTO = jogoClient.encontrarPorId(compraDTO.getJogoId());
        if (jogoDTO == null) {
            throw new IllegalArgumentException("Jogo não encontrado");
        }

        // Cria a compra
        Compra compra = new Compra();
        compra.setValorTotal(jogoDTO.getPreco() * compraDTO.getQuantidade());
        compra.setData(LocalDateTime.now());
        compra.setContaId(contaDTO.getId());

        if(compra.getValorTotal() > contaDTO.getSaldo()) {
            throw new IllegalArgumentException("Jogo não encontrado");
        }

        BeanUtils.copyProperties(compraDTO,compra,"data");

        Compra novaCompra = compraRepository.save(compra);

        // Enviar evento para adicionar o jogo à biblioteca
        compraProducer.sendMessageToBibliotecaQueue(novaCompra);

        // Enviar evento para atualizar a conta (saldo)
        compraProducer.sendMessageToContaQueue(novaCompra);

        return novaCompra;
    }

    public List<Compra> listarCompras() {
        return compraRepository.findAll();
    }

    public List<Compra> encontrarPorUsuarioId(Long id) {
        return compraRepository.encontrarPorUsuarioId(id);
    }

    public Optional<Compra> encontrarPorId(Long id) {
        return compraRepository.findById(id);
    }
}