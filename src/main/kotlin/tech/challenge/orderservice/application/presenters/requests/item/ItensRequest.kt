package tech.challenge.orderservice.application.presenters.requests.item

import java.util.*

data class ItensRequest(
        var produtoId: UUID? = null,

        var observacoes: String? = null,

        var quantidade: Int? = null,
)
