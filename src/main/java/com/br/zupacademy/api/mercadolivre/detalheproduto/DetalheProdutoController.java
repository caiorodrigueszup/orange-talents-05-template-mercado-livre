package com.br.zupacademy.api.mercadolivre.detalheproduto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.br.zupacademy.api.mercadolivre.cadastroproduto.Produto;

@RestController
public class DetalheProdutoController {

	@PersistenceContext
	private EntityManager manager;
	
	@GetMapping("/produtos/{id}")
	public ResponseEntity<DetalheProdutoResponse> detalhe(@PathVariable Long id) {
		Produto produto = manager.find(Produto.class, id);
		
		return ResponseEntity.ok(new DetalheProdutoResponse(produto));
	}
}
