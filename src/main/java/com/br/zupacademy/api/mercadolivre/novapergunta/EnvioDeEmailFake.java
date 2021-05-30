package com.br.zupacademy.api.mercadolivre.novapergunta;

import org.springframework.stereotype.Component;

import com.br.zupacademy.api.mercadolivre.novousuario.Usuario;

@Component
public class EnvioDeEmailFake {

	public void envia(String titulo, String nomeDoProduto, Usuario remetente, Usuario destinatario) {
		System.out.println("Olá " + destinatario.getUsername());
		System.out.println("Você tem uma nova pergunra no produto: " + nomeDoProduto);
		System.out.println("Do(a) consumidor(a) " + remetente.getUsername());
		System.out.println("Pergunta: " + titulo);
	}

}
