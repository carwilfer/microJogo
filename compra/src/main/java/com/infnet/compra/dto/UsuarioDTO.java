package com.infnet.compra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String nome;
    private String tipoUsuario; // Para o "ADMIN", etc.
    private double saldo;
}
