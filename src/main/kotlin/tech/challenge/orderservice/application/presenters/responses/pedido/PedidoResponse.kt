package tech.challenge.orderservice.application.presenters.responses.pedido

import tech.challenge.orderservice.application.presenters.responses.item.ItemResponse
import tech.challenge.orderservice.domain.enums.StatusPagamento
import tech.challenge.orderservice.domain.enums.StatusPedido
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class PedidoResponse(
    var id: UUID? = null,
    var clienteId: UUID? = null,
    var dataCriacao: LocalDateTime? = null,
    var dataDelecao: LocalDateTime? = null,
    var dataAtualizacao: LocalDateTime? = null,
    var statusPedido: StatusPedido? = null,
    var preco: BigDecimal? = null,
    var statusPagamento: StatusPagamento? = null,
    var itens: MutableList<ItemResponse> = mutableListOf()
) {
    fun adicionarItem(item: ItemResponse) {
        itens.add(item)
    }
}
