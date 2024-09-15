package com.infnet.conta.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private double saldo;

    @Column(name = "tipo_usuario")
    private String tipoUsuario;

    private Long usuarioId;

    @ElementCollection
    private List<Long> jogosIds;

    @ElementCollection
    private List<Long> compraIds;

    
}
