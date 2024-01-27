package tech.challenge.orderservice.application.presenters.requests.produto

import tech.challenge.orderservice.domain.enums.Categoria
import java.math.BigDecimal

data class AtualizarProdutoRequest(
        var nome: String? = null,
        var preco: BigDecimal? = null,
        var imagem: String? = null,
        var descricao: String? = null,
        var categoria: Categoria? = null,
)
