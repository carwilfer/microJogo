package com.infnet.usuario.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infnet.usuario.dto.EmpresaDTO;
import com.infnet.usuario.dto.JogadorDTO;
import com.infnet.usuario.dto.UsuarioDTO;
import com.infnet.usuario.model.Usuario;
import com.infnet.usuario.producer.RabbitMQProducer;
import com.infnet.usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        if (usuarioDTO.getCnpj() != null) {
            usuario.setTipoUsuario("EMPRESA");
            usuario.setCnpj(usuarioDTO.getCnpj());
            usuario.setRazaoSocial(usuarioDTO.getRazaoSocial());
        } else {
            usuario.setTipoUsuario("JOGADOR");
            usuario.setCpf(usuarioDTO.getCpf());
        }

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        // Criação da DTO concreta
        UsuarioDTO usuarioDTOCriado;
        if ("EMPRESA".equals(usuario.getTipoUsuario())) {
            usuarioDTOCriado = new EmpresaDTO(usuario.getNome(),
                    usuario.getEmail(),
                    usuario.getSenha(),
                    usuario.getAtivo(),
                    usuario.getRazaoSocial(),
                    usuario.getCnpj());
        } else {
            usuarioDTOCriado = new JogadorDTO(usuario.getNome(),
                    usuario.getEmail(),
                    usuario.getSenha(),
                    usuario.getAtivo(),
                    usuario.getCpf());
        }

        // Enviar mensagem para o RabbitMQ
        try {
            rabbitMQProducer.sendMessage(usuarioDTOCriado);
            // Se o usuário criado for uma empresa, enviar também para a fila empresaQueue
            if ("EMPRESA".equals(usuario.getTipoUsuario())) {
                rabbitMQProducer.sendMessageToEmpresaQueue(usuarioDTOCriado);
            }
            if ("JOGADOR".equals(usuario.getTipoUsuario())) {
                rabbitMQProducer.sendMessageToJogadorQueue(usuarioDTOCriado);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao processar mensagem JSON", e);
        }

        return usuarioDTOCriado;
    }

    public UsuarioDTO encontrarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return criarDTO(usuario);
    }

    public List<UsuarioDTO> listarTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream().map(this::criarDTO).collect(Collectors.toList());
    }

    private UsuarioDTO criarDTO(Usuario usuario) {
        if ("EMPRESA".equals(usuario.getTipoUsuario())) {
            return new EmpresaDTO(usuario.getNome(),
                    usuario.getEmail(),
                    usuario.getSenha(),
                    usuario.getAtivo(),
                    usuario.getRazaoSocial(),
                    usuario.getCnpj());
        } else {
            return new JogadorDTO(usuario.getNome(),
                    usuario.getEmail(),
                    usuario.getSenha(),
                    usuario.getAtivo(),
                    usuario.getCpf());
        }
    }

    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public void ativarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        usuario.setAtivo(true);
        usuarioRepository.save(usuario);
    }

    public void desativarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
    }
}
