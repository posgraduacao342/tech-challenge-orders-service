package tech.challenge.orderservice.helpers

import tech.challenge.orderservice.domain.enums.Categoria
import tech.challenge.orderservice.infrastructure.db.entity.ProdutoEntity
import java.util.*

class ProdutoEntityHelper {

    companion object{

        fun gerarProduto(): ProdutoEntity {
            return ProdutoEntity().apply {
                nome = "Classic Burger"
                preco = 20.00.toBigDecimal()
                imagem = "hamburguer.jpg"
                descricao = "Pão, hamburguer, queijo, alface"
                categoria = Categoria.LANCHE
                id = UUID.randomUUID()
            }
        }

        fun gerarListProdutos(): MutableList<ProdutoEntity> {
            return mutableListOf(gerarProduto())
        }
    }
}