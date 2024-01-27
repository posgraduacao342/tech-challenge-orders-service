package tech.challenge.orderservice.domain.usecases

import tech.challenge.orderservice.domain.entities.Item
import tech.challenge.orderservice.domain.ports.`in`.ItemUseCasesPort
import tech.challenge.orderservice.domain.ports.out.ItemAdapterPort
import java.util.*

class ItemUseCases(
    private val itemAdapter: ItemAdapterPort
) : ItemUseCasesPort {

    override fun buscarItensPorPedido(pedidoId: UUID): MutableList<Item?>? {
        return itemAdapter.buscarItensPorPedido(pedidoId)
    }

    override fun buscarItem(itemId: UUID): Item {
        return itemAdapter.buscarItem(itemId)
    }

    override fun atualizarObservacao(itensObservacao: List<Item?>): List<Item> {
        val ids = itensObservacao.map { it?.id }
        val itens = itemAdapter.buscarItemPorIds(ids)

        itens.forEach { item ->
            itensObservacao.forEach { itemObservacao ->
                if (item.id == itemObservacao?.id) {
                    item.observacoes = itemObservacao?.observacoes
                }
            }
        }
        itemAdapter.atualizarOuSalvarListaItem(itens)
        return itens
    }
}