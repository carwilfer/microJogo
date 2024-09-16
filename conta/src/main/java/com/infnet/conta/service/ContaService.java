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

        // Adiciona logs para depuração
        System.out.println("Admin recuperado: " + admin);
        System.out.println("usuario recuperado: " + usuario);

        // Verifica se o usuário tem permissão para criar uma conta
        if (admin == null || !"ADMIN".equals(admin.getTipoUsuario())) {
            System.out.println("Usuário não autorizado a criar contas.");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuário não autorizado a criar contas.");
        }

        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuário não autorizado a criar contas.");
        }

        Conta conta = new Conta();
        conta.setLimiteDisponivel(contaDTO.getLimiteDisponivel());
        conta.setAtivo(contaDTO.isAtivo());
        conta.setUsuarioId(contaDTO.getUsuarioId());
        conta.setTipoUsuario(usuario.getTipoUsuario());
        conta.setSaldo(contaDTO.getSaldo());
        conta.setCompraIds(new ArrayList<>());

        Conta savedConta = contaRepository.save(conta);
        return convertToDTO(savedConta);
    }

    public ContaDTO getContaById(Long id) {
        Optional<Conta> conta = contaRepository.findById(id);
        return conta.map(this::convertToDTO).orElse(null);
    }

    public Boolean atualizarSaldo(CompraDTO compraDTO, Long usuarioId) {
        UsuarioDTO usuario = usuarioClient.encontrarPorId(usuarioId);

        if (usuario == null || !"ADMIN".equals(usuario.getTipoUsuario())) {
            return false;
        }

        Conta conta = contaRepository.encontrarPorUsuarioId(compraDTO.getUsuarioId());

        if (conta == null) {
            return false; // Conta não encontrada
        }

        double novoSaldo = conta.getSaldo() - compraDTO.getValorTotal();
        double novoLimiteDisponivel = conta.getLimiteDisponivel() + compraDTO.getValorTotal();
        conta.setSaldo(novoSaldo);
        conta.setLimiteDisponivel(novoLimiteDisponivel);
        conta.getCompraIds().add(compraDTO.getJogoId()); // Usar o jogoId para a lista de compras
        contaRepository.save(conta);
        return true;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Boolean atualizarSaldoPorAdmin(Long contaId, double valor) {
        Conta conta = contaRepository.findById(contaId).orElse(null);

        if (conta == null) {
            return false;
        }

        double novoSaldo = conta.getSaldo() + valor;
        conta.setSaldo(novoSaldo);
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
