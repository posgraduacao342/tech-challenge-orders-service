package tech.challenge.orderservice.application.presenters.requests.pedido

import tech.challenge.orderservice.application.presenters.requests.item.ItensRequest
import java.math.BigDecimal
import java.util.UUID

data class CriarPedidoRequest(

        val idCliente: UUID? = null,

        val preco: BigDecimal? = null,

        val itens: List<ItensRequest>? = null,
)
