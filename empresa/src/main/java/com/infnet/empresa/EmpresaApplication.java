package com.infnet.empresa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EmpresaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmpresaApplication.class, args);
        System.out.println("MicroEmpresa API Rodando");
    }
}
