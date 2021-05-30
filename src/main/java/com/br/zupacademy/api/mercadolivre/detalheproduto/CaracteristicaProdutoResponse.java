package com.br.zupacademy.api.mercadolivre.detalheproduto;

import com.br.zupacademy.api.mercadolivre.cadastroproduto.CaracteristicaProduto;

public class CaracteristicaProdutoResponse {
	
	public String nome;
	public String descricao;
	
	public CaracteristicaProdutoResponse(CaracteristicaProduto caracteristica) {
		this.nome = caracteristica.getNome();
		this.descricao = caracteristica.getDescricao();
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
