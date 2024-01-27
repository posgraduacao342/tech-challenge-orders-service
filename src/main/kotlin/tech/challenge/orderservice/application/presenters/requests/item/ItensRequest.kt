package tech.challenge.orderservice.application.presenters.requests.item

import java.util.*

data class ItensRequest(
        val produtoId: UUID? = null,

        val obsevacoes: String? = null,

        val quantidade: Int? = null,
)
