package tech.challenge.orderservice.helpers

import tech.challenge.orderservice.domain.entities.Produto
import tech.challenge.orderservice.domain.enums.Categoria

class ProdutoHelper {

    companion object {
        fun gerarProduto(): Produto {
            return Produto(
                nome = "Classic Burger",
                preco = 10.00.toBigDecimal(),
                imagem = "hamburguer.jpg",
                descricao = "Pão, hamburguer, queijo, alface",
                categoria = Categoria.LANCHE,
            )
        }

        fun gerarProduto(produto: Produto): Produto {
            return Produto(
                nome = produto.nome,
                preco = produto.preco,
                imagem = produto.imagem,
                descricao = produto.descricao,
                categoria = produto.categoria,
            )
        }

        fun gerarListProdutos(): List<Produto> {
            return mutableListOf(gerarProduto())
        }
    }
}