package com.infnet.jogo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JogoDTO {
    private Long id;
    private String nome;
    private String descricao;
    private double preco;

    @NotNull
    private Long empresaId;
    private String cnpj;
    private String nomeEmpresa;
}
