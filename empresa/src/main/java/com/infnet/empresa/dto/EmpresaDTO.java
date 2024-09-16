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
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Ignorar campos nulos
public class EmpresaDTO {
    private Long id;
    private String nome;
    private String razaoSocial;
    private String cnpj;
    private Boolean ativo;

    // Construtor padrão
    public EmpresaDTO() {}

    @JsonCreator
    public EmpresaDTO(@JsonProperty("nome") String nome,
                      @JsonProperty("ativo") Boolean ativo,
                      @JsonProperty("cnpj") String cnpj,
                      @JsonProperty("razaoSocial") String razaoSocial) {
        this.nome = nome;
        this.ativo = ativo;
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
    }

    public EmpresaDTO(Empresa empresa) {
        this.id = empresa.getId();
        this.razaoSocial = empresa.getRazaoSocial();
        this.cnpj = empresa.getCnpj();
    }

    public Empresa toEntity() {
        Empresa empresa = new Empresa();
        empresa.setRazaoSocial(this.razaoSocial);
        empresa.setCnpj(this.cnpj);
        empresa.setAtivo(this.ativo != null ? this.ativo : true);
        return empresa;
    }
}
