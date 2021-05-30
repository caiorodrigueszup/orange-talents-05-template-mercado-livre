package com.br.zupacademy.api.mercadolivre.detalheproduto;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import com.br.zupacademy.api.mercadolivre.cadastroproduto.Opinioes;
import com.br.zupacademy.api.mercadolivre.cadastroproduto.Produto;

public class DetalheProdutoResponse {

	private String nome;
	private BigDecimal preco;
	private String descricao;
	private Set<String> linksImagens;
	private Set<CaracteristicaProdutoResponse> caracteristicas;
	private SortedSet<String> perguntas;
	private Set<Map<String, String>> opinioes;
	private Double mediaNotas;
	private int total;

	public DetalheProdutoResponse(Produto produto) {
		this.nome = produto.getNome();
		this.preco = produto.getValor();
		this.descricao = produto.getDescricao();
		this.caracteristicas = produto.mapeiaCaracteristicas(CaracteristicaProdutoResponse::new);
		this.linksImagens = produto.mapeiaImagens(imagem -> imagem.getLink());
		this.perguntas = produto.mapeiaPerguntas(pergunta -> pergunta.getTitulo());

		Opinioes opinioes = produto.getOpinioes();
		this.opinioes = opinioes.mapeiaOpinioes(opiniao -> {
			return Map.of("titulo", opiniao.getTitulo(), "descricao", opiniao.getDescricao());
		});

		this.mediaNotas = opinioes.media();
		this.total = opinioes.total();
	}

	public String getNome() {
		return nome;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public String getDescricao() {
		return descricao;
	}

	public Set<String> getLinksImagens() {
		return linksImagens;
	}

	public Set<CaracteristicaProdutoResponse> getCaracteristicas() {
		return caracteristicas;
	}

	public SortedSet<String> getPerguntas() {
		return perguntas;
	}

	public Set<Map<String, String>> getOpinioes() {
		return opinioes;
	}

	public Double getMediaNotas() {
		return mediaNotas;
	}

	public int getTotal() {
		return total;
	}
}
