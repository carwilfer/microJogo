package com.infnet.avaliacao.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AvaliacaoDTO {

    private Long id;
    private Long jogoId;
    private Long usuarioId;
    private int nota;
    private String comentario;
}