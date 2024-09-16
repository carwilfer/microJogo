package com.infnet.compra.dto;

import java.util.List;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContaDTO {
    private Long id;
    private double limiteDisponivel;
    private boolean ativo;
    private double saldo;
}
