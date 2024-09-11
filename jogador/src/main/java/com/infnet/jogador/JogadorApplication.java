package com.infnet.jogador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JogadorApplication {

	public static void main(String[] args) {
		SpringApplication.run(JogadorApplication.class, args);
		System.out.println("MicroJogador API Rodando");
	}
}
