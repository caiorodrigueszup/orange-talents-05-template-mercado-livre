package com.br.zupacademy.api.mercadolivre.novapergunta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.zupacademy.api.mercadolivre.novousuario.Usuario;

@Service
public class CentralEnvioDeEmails {
	
	@Autowired
	private EnvioDeEmailFake envioDeEmailFake; 
	
	public void enviaEmailNovaPergunta(Pergunta pergunta) {
		Usuario remetente = pergunta.getUsuario();
		Usuario destinatario = pergunta.donoDoProduto();
		String tituloPergunta = pergunta.getTitulo();
		String nomeDoProduto =  pergunta.nomeDoProduto();
		
		envioDeEmailFake.envia(tituloPergunta, nomeDoProduto, remetente, destinatario);
	}
}
