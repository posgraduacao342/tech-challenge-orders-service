package tech.challenge.orderservice.application.presenters.requests.produto

import tech.challenge.orderservice.domain.enums.Categoria
import java.math.BigDecimal

data class AtualizarProdutoRequest(
        val nome: String? = null,
        val preco: BigDecimal? = null,
        val imagem: String? = null,
        val descricao: String? = null,
        val categoria: Categoria? = null,
)
