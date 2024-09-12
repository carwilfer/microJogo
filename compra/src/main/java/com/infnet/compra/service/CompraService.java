package com.infnet.compra.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infnet.compra.model.Compra;
import com.infnet.compra.producer.CompraProducer;
import com.infnet.compra.repository.CompraRepository;
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

    public Compra criarCompra(Compra compra) throws JsonProcessingException {
        compra.setData(LocalDateTime.now());
        Compra novaCompra = compraRepository.save(compra);

        // Enviar evento de compra criada
        compraProducer.sendMessageToBibliotecaQueue(novaCompra);
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