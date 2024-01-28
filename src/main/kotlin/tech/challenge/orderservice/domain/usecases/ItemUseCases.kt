package tech.challenge.orderservice.domain.usecases

import tech.challenge.orderservice.domain.entities.Item
import tech.challenge.orderservice.domain.ports.`in`.ItemUseCasesPort
import tech.challenge.orderservice.domain.ports.out.ItemGatewayPort
import java.util.*

class ItemUseCases(
    private val itemGateway: ItemGatewayPort
) : ItemUseCasesPort {

    override fun buscarItensPorPedido(pedidoId: UUID): MutableList<Item>? {
        return itemGateway.buscarItensPorPedido(pedidoId)
    }

    override fun buscarItem(itemId: UUID): Item {
        return itemGateway.buscarItem(itemId)
    }

    override fun atualizarObservacao(itemId: UUID, observacoes: String): Item {
        return itemGateway.atualizarObervacoes(itemId, observacoes)
    }
}