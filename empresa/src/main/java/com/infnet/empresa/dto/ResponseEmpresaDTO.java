package com.infnet.empresa.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Ignorar campos nulos
public class ResponseEmpresaDTO {
    private Long id;
    private String razaoSocial;
    private String cnpj;
}
