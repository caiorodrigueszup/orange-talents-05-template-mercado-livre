package com.br.zupacademy.api.mercadolivre.novapergunta;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.br.zupacademy.api.mercadolivre.cadastroproduto.Produto;
import com.br.zupacademy.api.mercadolivre.novousuario.Usuario;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

public class NovaPerguntaRequest {
	
	@NotBlank
	private String titulo;
	
	@JsonCreator(mode = Mode.PROPERTIES)
	public NovaPerguntaRequest(@NotBlank String titulo) {
		this.titulo = titulo;
	}

	@Override
	public String toString() {
		return "NovaPerguntaRequest [titulo=" + titulo + "]";
	}
	
	public Pergunta toModel(@NotNull @Valid Usuario usuario, @NotNull @Valid Produto produto) {
		return new Pergunta(titulo, usuario, produto);
	}
}
