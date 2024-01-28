package tech.challenge.orderservice.domain.ports.out

import tech.challenge.orderservice.domain.entities.Item
import java.util.*

interface ItemGatewayPort {
    fun salvarItem(item: Item?): Item?
    fun atualizarObervacoes(itemId: UUID, observacoes: String): Item
    fun deletarItem(itemId: UUID?)
    fun buscarItensPorPedido(pedidoId: UUID): MutableList<Item>?
    fun deletarItensPorPedido(pedidoId: UUID?)
    fun buscarItem(itemId: UUID): Item
    fun buscarItemPorIds(ids: List<UUID?>): List<Item>
    fun atualizarOuSalvarListaItem(itens: List<Item?>): List<Item>
}