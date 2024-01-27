package tech.challenge.orderservice.application.presenters.requests.item

data class AtualizarItemRequest(
    val observacoes: String? = null,
    val quantidade: Int? = null
)
