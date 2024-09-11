package com.infnet.jogador.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Jogador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "nome")
    private String nome;

    @Email
    @NotBlank
    @Column(name = "email")
    private String email;

    @NotBlank(message = "A senha não deve estar em branco")
    @Column(name = "senha")
    private String senha;

    @Column(name = "ativo")
    private Boolean ativo = true;

    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "CPF não é válido")
    @NotBlank
    private String cpf;

    @ElementCollection
    @CollectionTable(name = "amizade", joinColumns = @JoinColumn(name = "jogador_id"))
    @Column(name = "amizade_id")
    private List<Long> amizades;
}
