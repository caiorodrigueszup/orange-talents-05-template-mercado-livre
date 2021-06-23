package com.br.zupacademy.api.mercadolivre.realizarcompra;

import com.br.zupacademy.api.mercadolivre.cadastrocategoria.Categoria;
import com.br.zupacademy.api.mercadolivre.cadastroproduto.CaracteristicaProduto;
import com.br.zupacademy.api.mercadolivre.cadastroproduto.NovaCaracteristicaRequest;
import com.br.zupacademy.api.mercadolivre.cadastroproduto.Produto;
import com.br.zupacademy.api.mercadolivre.novousuario.SenhaLimpa;
import com.br.zupacademy.api.mercadolivre.novousuario.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.platform.commons.annotation.Testable;

import java.math.BigDecimal;
import java.util.List;

@Testable
public class CompraControllerTest {

    @DisplayName("Verifica se tem estoque")
    @ParameterizedTest
    @CsvSource({ "1,1,true", "1,2,false", "4,2,true" })
    public void estoqueProduto(int estoque, int quantidadePedida, boolean resultadoEsperado) {
        List<NovaCaracteristicaRequest> caracteristicas = List.of(
                new NovaCaracteristicaRequest("Altura", "3.55"),
                new NovaCaracteristicaRequest("Largura", "5.33"),
                new NovaCaracteristicaRequest("Peso", "44.5kg")
        );

        Categoria categoria = new Categoria("Eletrodoméstico");
        Usuario usuario = new Usuario("caio@usp.br", new SenhaLimpa("senhaaaa"));
        Produto produto = new Produto("Geladeira", new BigDecimal(44600.0), estoque, caracteristicas,
                "geladeira", categoria, usuario);

        boolean resultado = produto.temEstoqueParaAbater(quantidadePedida);

        Assertions.assertEquals(resultadoEsperado, resultado);
    }

    @DisplayName("Não abate o estoque menor ou igual a zero")
    @ParameterizedTest
    @CsvSource({"0", "-1", "-100"})
    public void naoAbateEstoqueMenorOuIgualAZero(int estoque) {
        List<NovaCaracteristicaRequest> caracteristicas = List.of(
                new NovaCaracteristicaRequest("Altura", "3.55"),
                new NovaCaracteristicaRequest("Largura", "5.33"),
                new NovaCaracteristicaRequest("Peso", "44.5kg")
        );

        Categoria categoria = new Categoria("Eletrodoméstico");
        Usuario usuario = new Usuario("caio@usp.br", new SenhaLimpa("senhaaaa"));
        Produto produto = new Produto("Geladeira", new BigDecimal(44600.0), estoque, caracteristicas,
                "geladeira", categoria, usuario);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            produto.temEstoqueParaAbater(estoque);
        });
    }
}
