package com.infnet.usuario.dto;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JogadorDTO extends UsuarioDTO {
    private String cpf;

    public JogadorDTO(String nome, String email, String senha, Boolean ativo, String cpf) {
        super(nome, email, senha, ativo);
        this.cpf = cpf;
    }
}
