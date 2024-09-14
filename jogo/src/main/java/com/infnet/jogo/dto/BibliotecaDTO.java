package com.infnet.jogo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class BibliotecaDTO {
    private Long id;
    private LocalDateTime data;
    private BigDecimal valorTotal;
    private Integer quantidade;
    private Long usuarioId;
    private Long jogoId;
    private Long contaId;
}