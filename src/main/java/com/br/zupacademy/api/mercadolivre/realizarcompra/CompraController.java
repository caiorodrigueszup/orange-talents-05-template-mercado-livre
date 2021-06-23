package com.br.zupacademy.api.mercadolivre.realizarcompra;

import com.br.zupacademy.api.mercadolivre.cadastroproduto.Produto;
import com.br.zupacademy.api.mercadolivre.novapergunta.CentralEnvioDeEmails;
import com.br.zupacademy.api.mercadolivre.novapergunta.EnvioDeEmailFake;
import com.br.zupacademy.api.mercadolivre.novousuario.Usuario;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.function.ServerRequest;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CompraController {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private SistemaDePagamento sistemaDePagamento;

    @Autowired
    private EnvioDeEmailFake envioDeEmailFake;

    @PostMapping("/produtos/compras")
    @Transactional
    public ResponseEntity<?> realizarCompra(@RequestBody @Valid CompraRequest request,
                                         @AuthenticationPrincipal Usuario principal) {
        Produto produto = manager.find(Produto.class, request.getIdProduto());

        if (!produto.temEstoqueParaAbater(request.getQuantidade())) {
            Map<String, String> message = new HashMap<>();
            message.put("Error", "Não há produtos suficientes.");
            return ResponseEntity.unprocessableEntity().body(message);
        }

        Usuario usuarioInteressado = manager.find(Usuario.class, principal.getId());

        Compra compra = request.toModel(produto, usuarioInteressado);
        manager.persist(compra);

        envioDeEmailFake.envia("Solicitação de compra de produto", produto.getNome(), usuarioInteressado, produto.getUsuario());

        String URL = sistemaDePagamento.realiza(compra.getId(), compra.getGatewayPagamento());

        return ResponseEntity.status(HttpStatus.FOUND).body(URL);

    }
}
