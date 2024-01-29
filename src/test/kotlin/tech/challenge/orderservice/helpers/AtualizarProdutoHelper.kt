package tech.challenge.orderservice.helpers

import tech.challenge.orderservice.application.presenters.requests.produto.AtualizarProdutoRequest
import tech.challenge.orderservice.domain.enums.Categoria

class AtualizarProdutoHelper {

    companion object{
        fun gerarAtualizarProduto(): AtualizarProdutoRequest {
            return AtualizarProdutoRequest(
                nome = "Classic Burger",
                preco = 30.00.toBigDecimal(),
                imagem = "hamburguer.jpg",
                descricao = "Pão, hamburguer, queijo Cheddar, Bacon",
                categoria = Categoria.LANCHE
            )
        }
    }
}