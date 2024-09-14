package com.infnet.jogo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Jogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private double preco;

    @Column(nullable = false)
    private Long empresaId;
    private String cnpj;
    private String nomeEmpresa;

    @ManyToOne
    @JoinColumn(name = "biblioteca_id")  // Cria a coluna biblioteca_id na tabela Jogo
    private Biblioteca biblioteca;
}
