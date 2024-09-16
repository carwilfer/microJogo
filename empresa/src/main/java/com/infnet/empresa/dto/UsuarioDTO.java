package com.infnet.empresa.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class UsuarioDTO {
    private Long id;
    private String nome;
    private String email; // Pode ser removido se irrelevante
    private String senha;
    private Boolean ativo;
    private String tipoUsuario;
    private String razaoSocial;
    private String cnpj;
}
