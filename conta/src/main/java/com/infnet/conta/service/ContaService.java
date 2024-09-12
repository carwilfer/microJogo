package com.infnet.conta.service;

import com.infnet.conta.client.CompraClient;
import com.infnet.conta.dto.CompraDTO;
import com.infnet.conta.dto.ContaDTO;
import com.infnet.conta.model.Conta;
import com.infnet.conta.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private CompraClient compraClient;

    public ContaDTO createConta(ContaDTO contaDTO) {
        Conta conta = new Conta();
        conta.setLimiteDisponivel(contaDTO.getLimiteDisponivel());
        conta.setAtivo(contaDTO.isAtivo());
        conta.setUsuarioId(contaDTO.getUsuarioId());
        conta.setCompraIds(new ArrayList<>());
        Conta savedConta = contaRepository.save(conta);
        return convertToDTO(savedConta);
    }

    public ContaDTO getContaById(Long id) {
        Optional<Conta> conta = contaRepository.findById(id);
        return conta.map(this::convertToDTO).orElse(null);
    }

    public CompraDTO encontrarCompraPorId(Long id) {
        return compraClient.getCompraById(id).getBody();
    }

    public Boolean atualizarSaldo(CompraDTO compraDTO) {
        Conta conta = contaRepository.encontrarPorUsuarioId(compraDTO.getUsuarioId());

            double novoSaldo = conta.getSaldo() - compraDTO.getValorTotal();
            double novoLimiteDisponivel = conta.getLimiteDisponivel() + compraDTO.getValorTotal();
            conta.setSaldo(novoSaldo);
            conta.setLimiteDisponivel(novoLimiteDisponivel);
            conta.getCompraIds().add(compraDTO.getUsuarioId());
            contaRepository.save(conta);
        return true;
    }

    private ContaDTO convertToDTO(Conta conta) {
        ContaDTO dto = new ContaDTO();
        dto.setId(conta.getId());
        dto.setLimiteDisponivel(conta.getLimiteDisponivel());
        dto.setAtivo(conta.isAtivo());
        dto.setUsuarioId(conta.getUsuarioId());

        // Convertendo os IDs de compra para CompraDTOs
        List<CompraDTO> compras = conta.getCompraIds().stream()
                .map(compraId -> {
                    ResponseEntity<CompraDTO> response = compraClient.getCompraById(compraId);
                    return response.getBody(); // Pega o corpo do ResponseEntity
                })
                .collect(Collectors.toList());

        dto.setCompra(compras);

        dto.setSaldo(conta.getSaldo());
        return dto;
    }
}
