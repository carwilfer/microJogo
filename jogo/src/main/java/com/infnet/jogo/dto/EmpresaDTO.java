package com.infnet.jogo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmpresaDTO {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String papel;
    private String razaoSocial;

    @Pattern(regexp = "^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}$", message = "CNPJ is not valid") // Validação para CNPJ
    @NotBlank
    private String cnpj;
}
