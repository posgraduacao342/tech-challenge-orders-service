package tech.challenge.orderservice.domain.ports.`in`

import tech.challenge.orderservice.domain.entities.Item
import java.util.*

interface ItemUseCasesPort {
    fun buscarItensPorPedido(pedidoID: UUID): MutableList<Item?>?
    fun buscarItem(itemId: UUID): Item
    fun atualizarObservacao(itemId: UUID, observacoes: String): Item
}