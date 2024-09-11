package com.infnet.usuario.dto;

import com.infnet.usuario.model.Usuario;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // Ignorar campos nulos
public class UsuarioDTO {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private Boolean ativo = true;
    private String tipoUsuario;
    private String razaoSocial;
    private String cnpj;
    private String cpf;

    public UsuarioDTO() {}

    public UsuarioDTO(String nome, String email, String senha, Boolean ativo) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.ativo = ativo;
    }

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.senha = usuario.getSenha();
        this.ativo = usuario.getAtivo();
        this.tipoUsuario = usuario.getTipoUsuario();
        this.razaoSocial = usuario.getRazaoSocial();
        this.cnpj = usuario.getCnpj();
        this.cpf = usuario.getCpf();
    }
}
