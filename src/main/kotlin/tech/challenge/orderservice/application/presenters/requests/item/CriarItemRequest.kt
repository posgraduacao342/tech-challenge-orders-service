package tech.challenge.orderservice.application.presenters.requests.item

import java.util.*

data class CriarItemRequest(
        var pedidoId: UUID? = null,
        var produtoId: UUID? = null,
        var observacoes: String? = null,
        var quantidade: Int = 0
)
