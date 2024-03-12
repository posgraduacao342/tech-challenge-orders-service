package tech.challenge.orderservice.application.presenters.requests.pedido

import tech.challenge.orderservice.application.presenters.requests.item.ItensRequest
import tech.challenge.orderservice.domain.enums.MetodoPagamento
import java.math.BigDecimal
import java.util.UUID

data class CriarPedidoRequest(

        var clienteId: UUID? = null,

        var preco: BigDecimal? = null,

        var itens: List<ItensRequest>? = null,

        var metodoPagamento: MetodoPagamento? = null
)
