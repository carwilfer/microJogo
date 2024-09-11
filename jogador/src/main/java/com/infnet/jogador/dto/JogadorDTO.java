package com.infnet.jogador.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.infnet.jogador.model.Jogador;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL) // Ignorar campos nulos
public class JogadorDTO {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private boolean ativo;
    private String cpf;

    public JogadorDTO() {}

    @JsonCreator
    public JogadorDTO(@JsonProperty("nome") String nome,
                      @JsonProperty("email") String email,
                      @JsonProperty("senha") String senha,
                      @JsonProperty("ativo") boolean ativo, // Alterado para tipo primitivo
                      @JsonProperty("cpf") String cpf) { // Corrigido o nome do parâmetro
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.ativo = ativo;
        this.cpf = cpf;
    }

    public JogadorDTO(Jogador jogador) {
        this.id = jogador.getId();
        this.nome = jogador.getNome();
        this.email = jogador.getEmail();
        this.senha = jogador.getSenha();
        this.ativo = jogador.getAtivo();
        this.cpf = jogador.getCpf();
    }

    public Jogador toEntity() {
        Jogador jogador = new Jogador();
        jogador.setId(this.id);
        jogador.setNome(this.nome);
        jogador.setEmail(this.email);
        jogador.setSenha(this.senha);
        jogador.setAtivo(this.ativo);
        jogador.setCpf(this.cpf);
        return jogador;
    }
}
