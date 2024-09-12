package com.infnet.jogo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients(basePackages = "com.infnet.jogo.client")
public class JogoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JogoApplication.class, args);
		System.out.println("MicroJogo API Rodando");
	}
}
