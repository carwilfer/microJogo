package com.infnet.usuario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaDTO extends UsuarioDTO {
    private String razaoSocial;
    private String cnpj;

    public EmpresaDTO(String nome, String email, String senha, Boolean ativo, String cnpj, String razaoSocial) {
        super(nome, email, senha, ativo);
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
    }
}
