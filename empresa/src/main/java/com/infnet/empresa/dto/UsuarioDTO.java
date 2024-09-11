package com.infnet.empresa.dto;

import jakarta.validation.Valid;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class UsuarioDTO {
    private String nome;
    private String email;
    private String senha;
    private Boolean ativo;
    private String tipoUsuario;
    private String razaoSocial;
    private String cnpj;
}
