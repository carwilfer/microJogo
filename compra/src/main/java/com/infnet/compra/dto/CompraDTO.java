package com.infnet.compra.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CompraDTO {
    private Long id;
    private LocalDateTime data;
    private Long usuarioId;
    private Long jogoId;
    private int quantidade;
}
