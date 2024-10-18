package com.infnet.conta.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContaDTO {
    private Long id;
    private Long usuarioId;
    private Long adminId;
    private String tipoUsuario;
    private double limiteDisponivel;
    private boolean ativo;
    private List<CompraDTO> comprasIds = new ArrayList<>();
    private double saldo;
}