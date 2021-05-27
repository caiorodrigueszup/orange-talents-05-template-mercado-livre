package com.br.zupacademy.api.mercadolivre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class DesafioMercadoLivreApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioMercadoLivreApplication.class, args);
	}

}
