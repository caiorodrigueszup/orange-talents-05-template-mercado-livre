package com.br.zupacademy.api.mercadolivre.realizarcompra;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface SistemaDePagamento {
    public String realiza(UUID idCompra, FormaDePagamento gatewayPagamento);
}
