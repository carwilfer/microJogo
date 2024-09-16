package com.infnet.usuario.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infnet.usuario.dto.EmpresaDTO;
import com.infnet.usuario.dto.JogadorDTO;
import com.infnet.usuario.dto.UsuarioDTO;
import com.infnet.usuario.model.Usuario;
import com.infnet.usuario.producer.RabbitMQProducer;
import com.infnet.usuario.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RabbitMQProducer rabbitMQProducer;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, RabbitMQProducer rabbitMQProducer) {
        this.usuarioRepository = usuarioRepository;
        this.rabbitMQProducer = rabbitMQProducer;
    }

    public UsuarioDTO criarUsuario(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new IllegalArgumentException("Email já existente");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(usuarioDTO.getSenha());
        usuario.setAtivo(usuarioDTO.getAtivo());

        // Definindo o tipo de usuário e dados específicos
        if ("ADMIN".equals(usuarioDTO.getTipoUsuario())) {
            usuario.setTipoUsuario("ADMIN");
        } else if ("EMPRESA".equals(usuarioDTO.getTipoUsuario())) {
            usuario.setTipoUsuario("EMPRESA");
        } else if ("JOGADOR".equals(usuarioDTO.getTipoUsuario())) {
            usuario.setTipoUsuario("JOGADOR");
        } else {
            throw new IllegalArgumentException("Tipo de usuário inválido");
        }

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        try {
            if ("ADMIN".equals(usuario.getTipoUsuario())) {
                UsuarioDTO usuarioDTOCriado = new UsuarioDTO();
                BeanUtils.copyProperties(usuarioSalvo, usuarioDTOCriado);
            } else if ("EMPRESA".equals(usuario.getTipoUsuario())) {
                EmpresaDTO usuarioDTOCriado = new EmpresaDTO();
                BeanUtils.copyProperties(usuarioSalvo, usuarioDTOCriado);
                usuarioDTOCriado.setCnpj(usuarioDTO.getCnpj());
                usuarioDTOCriado.setRazaoSocial(usuarioDTO.getRazaoSocial());
                rabbitMQProducer.sendMessageToEmpresaQueue(usuarioDTOCriado);
            } else {
                JogadorDTO usuarioDTOCriado = new JogadorDTO();
                usuarioDTOCriado.setCpf(usuarioDTO.getCpf());
                BeanUtils.copyProperties(usuarioSalvo, usuarioDTOCriado);
                rabbitMQProducer.sendMessageToJogadorQueue(usuarioDTOCriado);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao processar mensagem JSON", e);
        }
        UsuarioDTO usuarioDTOCriado = new UsuarioDTO();
        BeanUtils.copyProperties(usuarioSalvo, usuarioDTOCriado);
        return usuarioDTOCriado;
    }

    public UsuarioDTO encontrarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        return criarDTO(usuario);
    }

    public List<UsuarioDTO> listarTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream().map(this::criarDTO).collect(Collectors.toList());
    }

    private UsuarioDTO criarDTO(Usuario usuario) {
        if ("EMPRESA".equals(usuario.getTipoUsuario())) {
            EmpresaDTO empresaDTO = new EmpresaDTO();
            BeanUtils.copyProperties(usuario, empresaDTO);
            return empresaDTO;
        } else {
            JogadorDTO jogadorDTO = new JogadorDTO();
            BeanUtils.copyProperties(usuario, jogadorDTO);
            return jogadorDTO;
        }
    }

    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public void ativarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        usuario.setAtivo(true);
        usuarioRepository.save(usuario);
    }

    public void desativarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
    }
}
