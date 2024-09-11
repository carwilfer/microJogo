package com.infnet.empresa.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.infnet.empresa.model.Empresa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL) // Ignorar campos nulos
public class EmpresaDTO {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private boolean ativo;
    private String razaoSocial;
    private String cnpj;

    // Construtor padrão
    public EmpresaDTO() {}

    @JsonCreator
    public EmpresaDTO(@JsonProperty("nome") String nome,
                      @JsonProperty("email") String email,
                      @JsonProperty("senha") String senha,
                      @JsonProperty("ativo") Boolean ativo,
                      @JsonProperty("cnpj") String cnpj,
                      @JsonProperty("razaoSocial") String razaoSocial) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.ativo = ativo;
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
    }

    public EmpresaDTO(Empresa empresa) {
        this.id = empresa.getId();
        this.razaoSocial = empresa.getRazaoSocial();
        this.cnpj = empresa.getCnpj();
        this.ativo = empresa.getAtivo();
    }
    // Método para converter DTO em entidade
    public Empresa toEntity() {
        Empresa empresa = new Empresa();
        empresa.setNome(this.nome);
        empresa.setEmail(this.email);
        empresa.setSenha(this.senha);
        empresa.setAtivo(this.ativo);
        empresa.setRazaoSocial(this.razaoSocial);
        empresa.setCnpj(this.cnpj);
        return empresa;
    }
}
