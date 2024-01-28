package tech.challenge.orderservice.helpers

import tech.challenge.orderservice.domain.entities.Produto
import tech.challenge.orderservice.domain.enums.Categoria

class ProdutoHelper {

    companion object {
        fun gerarProduto(): Produto {
            return Produto(
                nome = "Classic Burger",
                preco = 10.00.toBigDecimal(),
                imagem = "test",
                descricao = "Pão, hamburguer, queijo, alface",
                categoria = Categoria.LANCHE
            )
        }

        fun gerarListProdutos(): List<Produto> {
            return mutableListOf(gerarProduto())
        }
    }
}