package com.infnet.usuario.controller;

import com.infnet.usuario.Audit.UsuarioAuditService;
import com.infnet.usuario.configRabbit.RabbitConfig;
import com.infnet.usuario.dto.UsuarioDTO;
import com.infnet.usuario.model.Usuario;
import com.infnet.usuario.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioController.class);
    private final UsuarioService usuarioService;
    private final UsuarioAuditService usuarioAuditService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService, UsuarioAuditService usuarioAuditService) {
        this.usuarioService = usuarioService;
        this.usuarioAuditService = usuarioAuditService;
    }

    @PostMapping("/criar")
    public ResponseEntity<UsuarioDTO> criarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        LOGGER.info("Criando usuário: {}", usuarioDTO.getEmail());
        UsuarioDTO usuarioCriado = usuarioService.criarUsuario(usuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    @PostMapping("/criarLista")
    public ResponseEntity<List<UsuarioDTO>> criarUsuarios(@RequestBody List<UsuarioDTO> usuariosDTO) {
        List<UsuarioDTO> usuariosCriados = new ArrayList<>();
        for (UsuarioDTO usuarioDTO : usuariosDTO) {
            UsuarioDTO usuarioCriado = usuarioService.criarUsuario(usuarioDTO);
            usuariosCriados.add(usuarioCriado);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(usuariosCriados);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        List<UsuarioDTO> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obterUsuario(@PathVariable Long id) {
        UsuarioDTO usuario = usuarioService.encontrarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/ativar")
    public ResponseEntity<Void> ativarUsuario(@PathVariable Long id) {
        usuarioService.ativarUsuario(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/desativar")
    public ResponseEntity<Void> desativarUsuario(@PathVariable Long id) {
        usuarioService.desativarUsuario(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/historico")
    public ResponseEntity<List<Usuario>> obterHistorico(@PathVariable Long id) {
        List<Usuario> historico = usuarioAuditService.getRevisoes(id);
        return ResponseEntity.ok(historico);
    }
}
