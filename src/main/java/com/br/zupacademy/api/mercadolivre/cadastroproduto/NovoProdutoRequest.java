package com.br.zupacademy.api.mercadolivre.cadastroproduto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import com.br.zupacademy.api.mercadolivre.cadastrocategoria.Categoria;
import com.br.zupacademy.api.mercadolivre.compartilhado.ExistsId;
import com.br.zupacademy.api.mercadolivre.compartilhado.UniqueValue;
import com.br.zupacademy.api.mercadolivre.novousuario.Usuario;

public class NovoProdutoRequest {
	
	@NotBlank
	@UniqueValue(domainClass = Produto.class, fieldName = "nome")
	private String nome;
	
	@NotNull
	@Positive
	private BigDecimal valor;
	
	@Min(0)
	@NotNull
	private Long quantidadeDisponivel;
	
	@Size(min = 3)
	@Valid
	@NotNull
	private List<NovaCaracteristicaRequest> caracteristicas = new ArrayList<>();
	
	@NotBlank
	@Length(max = 1000)
	private String descricao;
	
	@NotNull
	@Valid
	@ExistsId(domainClass = Categoria.class, fieldName = "id")
	private Long idCategoria;

	public NovoProdutoRequest(@NotBlank String nome, @NotNull @Positive BigDecimal valor,
			@Min(0) @NotNull Long quantidadeDisponivel, @Size(min = 3) @Valid List<NovaCaracteristicaRequest> caracteristicas,
			@NotBlank @Length(max = 1000) String descricao, @NotNull @Valid Long idCategoria) {
		this.nome = nome;
		this.valor = valor;
		this.quantidadeDisponivel = quantidadeDisponivel;
		this.caracteristicas.addAll(caracteristicas);
		this.descricao = descricao;
		this.idCategoria = idCategoria;
	}

	@Override
	public String toString() {
		return "NovoProdutoRequest [nome=" + nome + ", valor=" + valor + ", quantidadeDisponivel="
				+ quantidadeDisponivel + ", caracteristicas=" + caracteristicas + ", descricao=" + descricao
				+ ", idCategoria=" + idCategoria + "]";
	}

	public Produto toModel(EntityManager manager, Usuario usuario) {
		Categoria categoria = manager.find(Categoria.class, idCategoria);
		
		return new Produto(nome, valor, quantidadeDisponivel, caracteristicas, descricao, categoria, usuario);
	}
}
