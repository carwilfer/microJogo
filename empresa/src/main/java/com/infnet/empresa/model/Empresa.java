package com.infnet.empresa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@AllArgsConstructor
@DiscriminatorValue("EMPRESA")
@Inheritance(strategy = InheritanceType.JOINED)
@EntityListeners(AuditingEntityListener.class)
public class Empresa {

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

    @Column(name = "razao_social")
    private String razaoSocial;

    @Pattern(regexp = "^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}$", message = "CNPJ is not valid")
    @NotBlank
    @Column(name = "cnpj")
    private String cnpj;

    // Construtor padrão
    public Empresa() {}
}
