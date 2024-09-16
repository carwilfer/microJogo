package com.infnet.usuario.dto;

import com.infnet.usuario.model.Usuario;
import lombok.*;

import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Ignorar campos nulos
public class UsuarioDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String razaoSocial;
    private String cnpj;
    private String email;
    private String senha;
    private Boolean ativo = true;
    private String tipoUsuario;
}
