package com.infnet.jogador.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class UsuarioDTO {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private Boolean ativo;
    private String tipoUsuario;
    private String cpf;
}
