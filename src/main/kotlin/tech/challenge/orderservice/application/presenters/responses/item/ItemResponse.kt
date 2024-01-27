package tech.challenge.orderservice.application.presenters.responses.item

import java.util.UUID

data class ItemResponse(
        var id: UUID? = null,

        var observacoes: String? = null,

        var quantidade: Int? = null,
)
