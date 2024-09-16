package com.infnet.conta.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpresaDTO {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private boolean ativo;
    private String razaoSocial;
    private String cnpj;
    private String tipoUsuario;
}
