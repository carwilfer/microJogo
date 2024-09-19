package com.infnet.conta.service;

import com.infnet.conta.client.CompraClient;
import com.infnet.conta.client.EmpresaClient;
import com.infnet.conta.client.JogadorClient;
import com.infnet.conta.client.UsuarioClient;
import com.infnet.conta.dto.*;
import com.infnet.conta.model.Conta;
import com.infnet.conta.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private CompraClient compraClient;

    @Autowired
    private UsuarioClient usuarioClient;

    public ContaDTO createConta(ContaDTO contaDTO) {
        // Obtém os detalhes do usuário
        UsuarioDTO admin = usuarioClient.encontrarPorId(contaDTO.getAdminId());
        UsuarioDTO usuario = usuarioClient.encontrarPorId(contaDTO.getUsuarioId());

        // Verifica se o admin tem permissão para criar conta
        if (admin == null || !"ADMIN".equals(admin.getTipoUsuario())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuário não autorizado.");
        }

        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuário não encontrado.");
        }

        // Criação da conta
        Conta conta = new Conta();
        conta.setLimiteDisponivel(contaDTO.getLimiteDisponivel());
        conta.setAtivo(contaDTO.isAtivo());
        conta.setUsuarioId(contaDTO.getUsuarioId());
        conta.setTipoUsuario(usuario.getTipoUsuario());
        conta.setSaldo(contaDTO.getSaldo());
        conta.setCompraIds(new ArrayList<>());

        // Salva a conta no repositório
        Conta savedConta = contaRepository.save(conta);
        ContaDTO savedContaDTO =  convertToDTO(savedConta);
        savedContaDTO.setTipoUsuario(admin.getTipoUsuario());
        savedContaDTO.setAdminId(admin.getId());
        return savedContaDTO;
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
        dto.setLimiteDisponivel(conta.getLimiteDisponivel());
        dto.setAtivo(conta.isAtivo());
        dto.setUsuarioId(conta.getUsuarioId());

        List<CompraDTO> compras = conta.getCompraIds().stream()
                .map(compraId -> {
                    ResponseEntity<CompraDTO> response = compraClient.getCompraById(compraId);
                    return response.getBody();
                })
                .collect(Collectors.toList());

        dto.setCompra(compras);
        dto.setSaldo(conta.getSaldo());
        return dto;
    }
}
