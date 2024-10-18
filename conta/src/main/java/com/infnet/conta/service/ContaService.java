package com.infnet.conta.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infnet.conta.dto.*;
import com.infnet.conta.model.Conta;
import com.infnet.conta.repository.ContaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    public Conta createConta(ContaDTO contaDTO) throws JsonProcessingException {
        Conta conta = new Conta();
        BeanUtils.copyProperties(contaDTO, conta);

        conta.setAdminId(contaDTO.getAdminId());
        conta.setTipoUsuario(contaDTO.getTipoUsuario());

        // Atualiza `compraIds` corretamente
        if (contaDTO.getComprasIds() != null && !contaDTO.getComprasIds().isEmpty()) {
            List<Long> compraIds = contaDTO.getComprasIds()
                    .stream()
                    .map(CompraDTO::getUsuarioId) // Altere para capturar o id correto da compra
                    .collect(Collectors.toList());
            conta.setCompraIds(compraIds);
        }

        Conta novaConta = contaRepository.save(conta);
        return novaConta;
    }

    public ContaDTO getContaById(Long id) {
        return contaRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public Boolean atualizarSaldo(CompraDTO compraDTO, Long usuarioId) {
        Conta conta = contaRepository.encontrarPorUsuarioId(usuarioId);
        if (conta == null) {
            throw new IllegalStateException("Conta não encontrada.");
        }

        double valorCompra = compraDTO.getValorTotal();
        if (conta.getSaldo() < valorCompra) {
            return false;
        }

        conta.setSaldo(conta.getSaldo() - valorCompra);
        contaRepository.save(conta);
        return true;
    }

    public Boolean atualizarSaldoPorAdmin(Long contaId, double valor) {
        Conta conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new IllegalStateException("Conta não encontrada"));

        if (!"ADMIN".equals(conta.getTipoUsuario())) {
            return false;
        }

        conta.setSaldo(conta.getSaldo() + valor);
        contaRepository.save(conta);
        return true;
    }

    public ContaDTO encontrarPorUsuarioId(Long id) {
        Conta conta = contaRepository.encontrarPorUsuarioId(id);
        return convertToDTO(conta);
    }

    private ContaDTO convertToDTO(Conta conta) {
        ContaDTO dto = new ContaDTO();
        dto.setId(conta.getId());
        dto.setAdminId(conta.getAdminId());
        dto.setTipoUsuario(conta.getTipoUsuario());
        dto.setLimiteDisponivel(conta.getLimiteDisponivel());
        dto.setAtivo(conta.isAtivo());
        dto.setUsuarioId(conta.getUsuarioId());
        dto.setSaldo(conta.getSaldo());

        // Preencher a lista de compraIds corretamente
        List<CompraDTO> comprasIds = new ArrayList<>();
        for (Long compraId : conta.getCompraIds()) {
            // Aqui você deve buscar a compra pelo ID, se necessário, ou criar um DTO vazio
            CompraDTO compraDTO = new CompraDTO();
            compraDTO.setUsuarioId(compraId); // ou outros campos relevantes
            comprasIds.add(compraDTO);
        }
        dto.setComprasIds(comprasIds);

        return dto;
    }
}
