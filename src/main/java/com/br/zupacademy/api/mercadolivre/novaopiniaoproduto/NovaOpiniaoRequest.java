package com.br.zupacademy.api.mercadolivre.novaopiniaoproduto;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.br.zupacademy.api.mercadolivre.cadastroproduto.Produto;
import com.br.zupacademy.api.mercadolivre.novousuario.Usuario;

public class NovaOpiniaoRequest {
	
	@NotNull
	@Min(1)@Max(5)
	private Integer nota;
	
	@NotBlank
	private String titulo;
	
	@NotBlank
	@Length(max = 500)
	private String descricao;

	public NovaOpiniaoRequest(@NotNull @Min(1) @Max(5) Integer nota, @NotBlank String titulo,
			@NotBlank @Length(max = 500) String descricao) {
		this.nota = nota;
		this.titulo = titulo;
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return "NovaOpiniaoRequest [nota=" + nota + ", titulo=" + titulo + ", descricao=" + descricao + "]";
	}
	
	public Opiniao toModel(@NotNull @Valid Produto produto, @NotNull @Valid Usuario usuario) {
		return new Opiniao(nota, titulo, descricao, usuario, produto);
	}
}
