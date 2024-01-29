package tech.challenge.orderservice.application.presenters.requests.pedido

import tech.challenge.orderservice.application.presenters.requests.item.ItensRequest
import java.math.BigDecimal
import java.util.UUID

data class CriarPedidoRequest(

        var idCliente: UUID? = null,

        var preco: BigDecimal? = null,

        var itens: List<ItensRequest>? = null,
)