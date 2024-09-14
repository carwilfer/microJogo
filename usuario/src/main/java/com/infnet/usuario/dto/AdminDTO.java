package com.infnet.usuario.dto;

public class AdminDTO extends UsuarioDTO {

    public AdminDTO(String nome, String email, String senha, Boolean ativo) {
        super(nome, email, senha, ativo);
    }

    @Override
    public String toString() {
        return "AdminDTO{" +
                "nome='" + getNome() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", senha='" + getSenha() + '\'' +
                ", ativo=" + getAtivo() +
                '}';
    }
}
