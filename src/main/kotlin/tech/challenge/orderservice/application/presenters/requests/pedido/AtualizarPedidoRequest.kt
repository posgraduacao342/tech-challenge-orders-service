package tech.challenge.orderservice.application.presenters.requests.pedido

import tech.challenge.orderservice.domain.enums.StatusPedido

data class AtualizarPedidoRequest(

    var statusPedido: StatusPedido? = null
)
