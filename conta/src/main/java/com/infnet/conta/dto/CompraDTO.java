package com.infnet.conta.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompraDTO {

    private Long usuarioId;
    private Long jogoId;
    private float valorTotal;
}
