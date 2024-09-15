package com.infnet.compra.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infnet.compra.client.JogoClient;
import com.infnet.compra.client.UsuarioClient;
import com.infnet.compra.dto.CompraDTO;
import com.infnet.compra.dto.UsuarioDTO;
import com.infnet.compra.model.Compra;
import com.infnet.compra.producer.CompraProducer;
import com.infnet.compra.repository.CompraRepository;
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

    public Compra criarCompra(Compra compra) throws JsonProcessingException {
        // Verifique o usuário e o saldo
        ResponseEntity<UsuarioDTO> usuarioResponse = usuarioClient.encontrarPorId(compra.getUsuarioId());
        UsuarioDTO usuario = usuarioResponse.getBody();
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        // Verifique o jogo
        ResponseEntity<CompraDTO> jogoResponse = jogoClient.encontrarPorId(compra.getJogoId());
        CompraDTO jogo = jogoResponse.getBody();
        if (jogo == null) {
            throw new IllegalArgumentException("Jogo não encontrado");
        }

        // Cria a compra
        compra.setData(LocalDateTime.now());
        Compra novaCompra = compraRepository.save(compra);

        // Atualizar saldo do usuário
        Double saldoAtual = usuario.getSaldo();
        if (saldoAtual < novaCompra.getValorTotal()) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
        usuario.setSaldo(saldoAtual - novaCompra.getValorTotal());

        // Atualizar o saldo do usuário no serviço de usuário
        usuarioClient.atualizarSaldo(usuario.getId(), usuario.getSaldo());

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