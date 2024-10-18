package com.infnet.bff.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infnet.bff.client.CompraClient;
import com.infnet.bff.client.ContaClient;
import com.infnet.bff.client.JogoClient;
import com.infnet.bff.client.UsuarioClient;
import com.infnet.bff.dto.CompraDTO;
import com.infnet.bff.dto.ContaDTO;
import com.infnet.bff.dto.JogoDTO;
import com.infnet.bff.dto.UsuarioDTO;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Service
public class BffCompraService {

    @Autowired
    CompraClient compraClient;

    @Autowired
    ContaClient contaClient;

    @Autowired
    UsuarioClient usuarioClient;

    @Autowired
    JogoClient jogoClient;

    public CompraDTO criarCompra(CompraDTO compraDTO) throws JsonProcessingException {
        // Verifique o usuário e o saldo
        UsuarioDTO usuario = usuarioClient.encontrarPorId(compraDTO.getUsuarioId());
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        ContaDTO contaDTO = contaClient.encontrarPorId(compraDTO.getUsuarioId());
        System.out.println(contaDTO.toString());
        if (contaDTO == null) {
            throw new IllegalArgumentException("Jogo não encontrado");
        }

        // Verifique o jogo
        JogoDTO jogoDTO = jogoClient.encontrarPorId(compraDTO.getJogoId());
        if (jogoDTO == null) {
            throw new IllegalArgumentException("Jogo não encontrado");
        }
        // Cria a compra

        compraDTO.setValorTotal(jogoDTO.getPreco() * compraDTO.getQuantidade());
        compraDTO.setData(LocalDateTime.now());
        compraDTO.setContaId(contaDTO.getId());
        System.out.println(compraDTO.getContaId());

        if(compraDTO.getValorTotal() > contaDTO.getSaldo()) {
            throw new IllegalArgumentException("Jogo não encontrado");
        }

        CompraDTO novaCompra = compraClient.criarCompra(compraDTO);
        return novaCompra;

    }
}
