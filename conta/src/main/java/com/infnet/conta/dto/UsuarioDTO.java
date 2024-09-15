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
    private String tipoUsuario; // Para o "ADMIN", etc.
    private String cpf;
    private String cnpj;
}
