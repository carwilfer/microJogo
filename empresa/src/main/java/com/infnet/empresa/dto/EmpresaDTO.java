package com.infnet.empresa.dto;

import com.infnet.empresa.model.Empresa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
public class EmpresaDTO {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private Boolean ativo = true;
    private String razaoSocial;

    @Pattern(regexp = "^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}$", message = "CNPJ is not valid")
    @NotBlank
    private String cnpj;

    public EmpresaDTO() {}

    public EmpresaDTO(Empresa empresa) {
        this.id = empresa.getId();
        this.razaoSocial = empresa.getRazaoSocial();
        this.cnpj = empresa.getCnpj();
        this.ativo = empresa.getAtivo();
    }

    public EmpresaDTO(Long id, String razaoSocial, String cnpj, Boolean ativo) {
        this.id = id;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.ativo = ativo;
    }

    public Empresa toEntity() {
        Empresa empresa = new Empresa();
        empresa.setNome(this.nome);
        empresa.setEmail(this.email);
        empresa.setSenha(this.senha);
        empresa.setAtivo(this.ativo);
        empresa.setCnpj(this.cnpj);
        empresa.setRazaoSocial(this.razaoSocial);
        return empresa;
    }
}
