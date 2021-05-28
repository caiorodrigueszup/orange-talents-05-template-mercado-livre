package com.br.zupacademy.api.mercadolivre.cadastrocategoria;

import javax.validation.constraints.NotBlank;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ResponseStatusException;

import com.br.zupacademy.api.mercadolivre.compartilhado.UniqueValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

public class NovaCategoriaRequest {

	@NotBlank
	@UniqueValue(domainClass = Categoria.class, fieldName = "nome")
	private String nome;
	
	@Nullable
	private Long idCategoriaMae;

	@JsonCreator(mode = Mode.PROPERTIES)
	public NovaCategoriaRequest(String nome) {
		this.nome = nome;
	}

	public Long getIdCategoriaMae() {
		return idCategoriaMae;
	}

	@Override
	public String toString() {
		return "NovaCategoriaRequest [nome=" + nome + ", idCategoriaMae=" + idCategoriaMae + "]";
	}

	public Categoria toModel(CategoriaRepository categoriaRepository) {
		Categoria categoria = new Categoria(nome);

		if (idCategoriaMae != null) {
			Categoria possivelCategoriaMae = categoriaRepository.findById(idCategoriaMae)
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Categoria não encontrada"));

			categoria.setCategoriaMae(possivelCategoriaMae);
		}

		return categoria;
	}
}
