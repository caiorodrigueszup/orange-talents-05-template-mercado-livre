package com.br.zupacademy.api.mercadolivre.novapergunta;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.br.zupacademy.api.mercadolivre.cadastroproduto.Produto;
import com.br.zupacademy.api.mercadolivre.novousuario.Usuario;

@RestController
public class CadastroPerguntaController {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private CentralEnvioDeEmails centralEnvioDeEmails;
	
	@PostMapping("/produtos/{idProduto}/perguntas")
	@Transactional
	public String cadastrar(@RequestBody @Valid NovaPerguntaRequest request,
			@PathVariable Long idProduto, @AuthenticationPrincipal Usuario usuario) {
		
		Produto produto = manager.find(Produto.class, idProduto);
		
		if (produto == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Este produto não existe");
		}
		
		Pergunta novaPergunta = request.toModel(usuario, produto);
		
		manager.persist(novaPergunta);
		
		centralEnvioDeEmails.enviaEmailNovaPergunta(novaPergunta);
		
		return novaPergunta.toString();
	}
}
