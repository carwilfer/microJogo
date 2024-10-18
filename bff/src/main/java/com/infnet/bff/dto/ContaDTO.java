package com.infnet.bff.dto;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContaDTO {

    private Long id;
    private Long usuarioId;
    private Long adminId;
    private String tipoUsuario;
    private double limiteDisponivel;
    private boolean ativo;
    private List<CompraDTO> compra;
    private double saldo;
}
