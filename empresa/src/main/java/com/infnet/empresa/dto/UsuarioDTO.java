package com.infnet.empresa.dto;

import lombok.Data;

@Data
public class UsuarioDTO {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private Boolean ativo = true;
    private String tipoUsuario;
    private String cnpj;
    private String razaoSocial;
    private String cpf;
}
