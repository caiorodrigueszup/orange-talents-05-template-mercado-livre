package com.br.zupacademy.api.mercadolivre.detalheproduto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.br.zupacademy.api.mercadolivre.novousuario.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.br.zupacademy.api.mercadolivre.cadastroproduto.Produto;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class DetalheProdutoController {

	@PersistenceContext
	private EntityManager manager;
	
	@GetMapping("/produtos/{id}")
	public ResponseEntity<DetalheProdutoResponse> detalhe(@PathVariable Long id, @AuthenticationPrincipal Usuario principal) {
		Produto produto = manager.find(Produto.class, id);

		if (produto == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado");
		}

		return ResponseEntity.ok(new DetalheProdutoResponse(produto));
	}
}
