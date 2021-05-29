package com.br.zupacademy.api.mercadolivre.cadastroproduto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.zupacademy.api.mercadolivre.novousuario.Usuario;

@RestController
@RequestMapping("/produtos")
public class CadastroProdutoController {

	@PersistenceContext
	private EntityManager manager;
	
	
	@PostMapping
	@Transactional
	public String cadastrar(@RequestBody @Valid NovoProdutoRequest request) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		Usuario usuarioAutenticado = (Usuario) authentication.getPrincipal();
		
		Produto novoProduto = request.toModel(manager, usuarioAutenticado);
		manager.persist(novoProduto);
		return novoProduto.toString();
	}
}
