package com.infnet.usuario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Audited
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "usuario_tipo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USUARIO_ID")
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

    @Column(name = "tipo_usuario")
    private String tipoUsuario; // Pode ser 'EMPRESA', 'JOGADOR', 'ADMIN'
}
