package com.br.zupacademy.api.mercadolivre.realizarcompra;

import com.br.zupacademy.api.mercadolivre.cadastroproduto.Produto;
import com.br.zupacademy.api.mercadolivre.compartilhado.ExistsId;
import com.br.zupacademy.api.mercadolivre.novousuario.Usuario;
import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Locale;

public class CompraRequest {

    @NotNull
    @ExistsId(domainClass = Produto.class, fieldName = "id", message = "Este produto não existe.")
    private Long idProduto;

    @Positive
    @NotNull
    private Integer quantidade;

    @NotNull
    @Enumerated(EnumType.STRING)
    private FormaDePagamento gatewayPagamento;

    public CompraRequest(@NotNull Long idProduto, @Positive @NotNull Integer quantidade,
                         @NotNull FormaDePagamento gatewayPagamento) {
        this.idProduto = idProduto;
        this.quantidade = quantidade;
        this.gatewayPagamento = gatewayPagamento;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public FormaDePagamento getGatewayPagamento() {
        return gatewayPagamento;
    }

    @Override
    public String toString() {
        return "CompraRequest{" +
                "idProduto=" + idProduto +
                ", quantidade=" + quantidade +
                ", gatewayPagamento='" + gatewayPagamento.toString() + '\'' +
                '}';
    }

    public Compra toModel(Produto produto, Usuario usuarioInteressado) {
        return new Compra(produto, quantidade, gatewayPagamento, usuarioInteressado);
    }
}
