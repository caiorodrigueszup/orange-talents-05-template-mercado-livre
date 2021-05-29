package com.br.zupacademy.api.mercadolivre.cadastroimagemparaproduto;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.br.zupacademy.api.mercadolivre.cadastroproduto.Produto;
import com.br.zupacademy.api.mercadolivre.novousuario.Usuario;

@RestController
@RequestMapping("/produtos")
public class CadastroImagemProdutoController {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private Uploader fakeUpload; 
	
	@PostMapping("/{idProduto}/imagens/upload")
	@Transactional
	public String cadastrarImagens(@Valid ImagensRequest request, @PathVariable Long idProduto) {
		Produto produto = buscarProduto(idProduto);
		
		if (!produto.pertenceAoUsuario(autenticado())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, 
					"Você não tem permissão para inserir imagem à outro usuário.");
		}
		
		Set<String> links = fakeUpload.envia(request.getImagens());
		
		produto.associaImagens(links);
		manager.merge(produto);
	
		return produto.toString();
	}

	private Usuario autenticado() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (Usuario) authentication.getPrincipal();
	}

	private Produto buscarProduto(Long idProduto) {
		Produto produto = manager.find(Produto.class, idProduto);
		
		if (produto == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Este produto não existe");
		}
		
		return produto;
	}
}
