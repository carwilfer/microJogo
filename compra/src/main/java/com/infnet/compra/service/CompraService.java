package com.infnet.compra.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infnet.compra.dto.CompraDTO;
import com.infnet.compra.model.Compra;
import com.infnet.compra.producer.CompraProducer;
import com.infnet.compra.repository.CompraRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Compra criarCompra(CompraDTO compraDTO) throws JsonProcessingException {

        Compra compra = new Compra();
        BeanUtils.copyProperties(compraDTO,compra,"data");
        compra.setData(LocalDateTime.now());
        compra.setContaId(compraDTO.getContaId());
        System.out.println(compraDTO.getContaId());

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