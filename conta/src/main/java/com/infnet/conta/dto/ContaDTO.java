package com.infnet.conta.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContaDTO {
    private Long id;
    private double limiteDisponivel;
    private boolean ativo;
    private Long usuarioId;
    private List<CompraDTO> compra;
    private double saldo;
}