package tech.challenge.orderservice.application.presenters.requests.item

import jakarta.validation.constraints.NotBlank
import java.util.UUID

data class AtualizarObservacaoItemRequest(
    @field:NotBlank
    var id: UUID? = null,
    @field:NotBlank
    var observacoes: String? = null
)
