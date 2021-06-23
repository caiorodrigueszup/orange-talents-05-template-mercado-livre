package com.br.zupacademy.api.mercadolivre.realizarcompra;

import com.br.zupacademy.api.mercadolivre.cadastroproduto.Produto;
import com.br.zupacademy.api.mercadolivre.novousuario.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class Compra {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @NotNull
    @ManyToOne
    private Produto produto;

    @NotNull
    @Positive
    private Integer quantidade;

    @NotNull
    @Enumerated(EnumType.STRING)
    private FormaDePagamento gatewayPagamento;

    private BigDecimal precoAtualProduto;

    @Enumerated(EnumType.STRING)
    private StatusCompra status;

    @NotNull
    @ManyToOne
    private Usuario usuario;

    public Compra(@NotNull Produto produto, @NotNull @Positive Integer quantidade,
                  @NotNull FormaDePagamento gatewayPagamento, @NotNull Usuario usuario) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.gatewayPagamento = gatewayPagamento;
        this.precoAtualProduto = produto.getValor();
        this.status = StatusCompra.INICIADA;
        this.usuario = usuario;
    }

    public UUID getId() {
        return id;
    }

    public Produto getProduto() {
        return produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public FormaDePagamento getGatewayPagamento() {
        return gatewayPagamento;
    }

    public BigDecimal getPrecoAtualProduto() {
        return precoAtualProduto;
    }

    public StatusCompra getStatus() {
        return status;
    }

    public Usuario getIdUsuario() {
        return usuario;
    }

    @Override
    public String toString() {
        return "Compra{" +
                "id=" + id +
                ", produto=" + produto +
                ", quantidade=" + quantidade +
                ", gatewayPagamento='" + gatewayPagamento + '\'' +
                ", precoAtualProduto=" + precoAtualProduto +
                ", status=" + status +
                ", idUsuario=" + usuario +
                '}';
    }
}
