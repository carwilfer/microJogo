package com.infnet.usuario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmpresaDTO extends UsuarioDTO {
    private String razaoSocial;
    private String cnpj;
}
