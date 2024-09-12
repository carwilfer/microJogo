package com.infnet.compra.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CompraDTO {
    private Long id;
    private LocalDateTime data;
    private Long usuarioId;
    private Long jogoId;
    private Long contaId;
    private Double valorTotal;
    private int quantidade;
}
