package com.infnet.conta.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
}
