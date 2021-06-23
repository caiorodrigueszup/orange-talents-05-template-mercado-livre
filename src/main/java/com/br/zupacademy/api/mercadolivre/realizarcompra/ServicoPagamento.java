package com.br.zupacademy.api.mercadolivre.realizarcompra;

import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.UUID;

@Component
public class ServicoPagamento implements SistemaDePagamento{
    @Override
    public String realiza(UUID idCompra, FormaDePagamento gatewayPagamento) {

        var urlPayPal = gatewayPagamento.toString().toLowerCase()+".com?buyerId="+idCompra+"&redirectUrl=http://localhost:8080/compras";
        var urlPagSeguro = gatewayPagamento.toString().toLowerCase()+".com?returnId="+idCompra+"&redirectUrl=http://localhost:8080/compras";
        return FormaDePagamento.paypal == gatewayPagamento ? urlPayPal : urlPagSeguro;




    }
}
