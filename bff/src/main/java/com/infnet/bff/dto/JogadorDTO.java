package com.infnet.bff.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JogadorDTO {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String tipoUsuario;
    private boolean ativo;
    private String cpf;
}
