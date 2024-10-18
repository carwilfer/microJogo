package com.infnet.conta.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double limiteDisponivel;
    private boolean ativo;
    private Long adminId;
    private double saldo;

    @Column(name = "tipo_usuario")
    private String tipoUsuario;

    // Adicione de volta o campo usuarioId
    private Long usuarioId;

    @ElementCollection
    private List<Long> jogosIds = new ArrayList<>();

    @ElementCollection
    private List<Long> compraIds = new ArrayList<>();

}
