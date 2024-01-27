package tech.challenge.orderservice.application.presenters.responses.item

import java.util.UUID

data class ItemResponse(
        val id: UUID? = null,

        val obsevacoes: String? = null,

        val quantidade: Int? = null,
)
