package com.infnet.bff.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompraDTO {

    private Long id;
    private LocalDateTime data;
    private Long usuarioId;
    private Long jogoId;
    private int quantidade;
    private double valorTotal;
    private Long contaId;
}
