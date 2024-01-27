package tech.challenge.orderservice.application.presenters.requests.item

import jakarta.validation.constraints.NotBlank
import java.util.UUID

data class AtualizarObservacaoItemRequest(
    @field:NotBlank
    val id: UUID? = null,
    @field:NotBlank
    val observacoes: String? = null
)
