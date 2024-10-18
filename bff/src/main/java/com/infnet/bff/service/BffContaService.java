package com.infnet.bff.service;

import com.infnet.bff.client.CompraClient;
import com.infnet.bff.client.ContaClient;
import com.infnet.bff.client.UsuarioClient;
import com.infnet.bff.dto.CompraDTO;
import com.infnet.bff.dto.ContaDTO;
import com.infnet.bff.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BffContaService {

    @Autowired
    private CompraClient compraClient;

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private ContaClient contaClient;

    public ContaDTO criarConta(ContaDTO contaDTO) {

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
        ContaDTO conta = new ContaDTO();
        conta.setLimiteDisponivel(contaDTO.getLimiteDisponivel());
        conta.setAtivo(contaDTO.isAtivo());
        conta.setUsuarioId(contaDTO.getUsuarioId());
        conta.setTipoUsuario(usuario.getTipoUsuario());
        conta.setAdminId(contaDTO.getAdminId());
        System.out.println(conta.getTipoUsuario());
        System.out.println(usuario.getTipoUsuario());
        conta.setSaldo(contaDTO.getSaldo());
        conta.setCompra(new ArrayList<>());
        System.out.println("Tipo de Usuário do Admin: " + admin.getTipoUsuario());
        System.out.println("Tipo de Usuário do Usuário: " + usuario.getTipoUsuario());

        // Salva a conta no repositório
        ContaDTO savedContaDTO = contaClient.criarConta(contaDTO);
        return savedContaDTO;

    }
}
