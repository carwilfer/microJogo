package com.infnet.avaliacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients(basePackages = "com.infnet.avaliacao.client")
public class avaliacaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(avaliacaoApplication.class, args);
		System.out.println("MicroAvaliação API Rodando");
	}
}
