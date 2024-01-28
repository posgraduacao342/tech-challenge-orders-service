package tech.challenge.orderservice.application.gateway

import org.springframework.stereotype.Component
import tech.challenge.orderservice.application.presenters.mappers.ItemMapper
import tech.challenge.orderservice.domain.entities.Item
import tech.challenge.orderservice.domain.ports.out.ItemGatewayPort
import tech.challenge.orderservice.infrastructure.db.entity.ItemEntity
import tech.challenge.orderservice.infrastructure.db.repositories.ItemRepository
import java.util.*
import java.util.stream.Collectors

@Component
class ItemGateway(
    private val itemRepository: ItemRepository,
    private val itemMapper: ItemMapper
) : ItemGatewayPort {

    override fun salvarItem(item: Item?): Item? {
        val itemEntity = itemMapper.toEntity(item)
        val savedItemEntity = itemEntity?.let { itemRepository.save(it) }
        return savedItemEntity?.let { itemMapper.toDomain(it) }
    }

    override fun atualizarObervacoes(itemId: UUID, observacoes: String): Item {
        val itemEntity = itemRepository.findById(itemId)
        itemEntity.get().observacoes = observacoes
        val updatedItemEntity = itemRepository.save(itemEntity.get())
        return itemMapper.toDomain(updatedItemEntity)!!
    }

    override fun deletarItem(itemId: UUID?) {
        itemRepository.deleteById(itemId!!)
    }

    override fun buscarItensPorPedido(pedidoId: UUID): MutableList<Item>? {
        val itemEntities = itemRepository.findByPedidoId(pedidoId)
        return itemEntities.stream()
            .map(itemMapper::toDomain)
            .collect(Collectors.toList())
    }

    override fun deletarItensPorPedido(pedidoId: UUID?) {
        val itemEntities: List<ItemEntity> = itemRepository.findByPedidoId(pedidoId)
        itemRepository.deleteAll(itemEntities)
    }

    override fun buscarItem(itemId: UUID): Item {
        val itemEntity: ItemEntity = itemRepository.findById(itemId)
            .orElseThrow { RuntimeException("Item não encontrado") }
        return itemMapper.toDomain(itemEntity)!!
    }

    override fun buscarItemPorIds(ids: List<UUID?>): List<Item> {
        val itemEntity: List<ItemEntity> = itemRepository.findByIdIn(ids)
        return itemMapper.toDomain(itemEntity)
    }

    override fun atualizarOuSalvarListaItem(itens: List<Item?>): List<Item> {
        val itensEntity = itemMapper.toEntity(itens)
        val itensSalvos = itemRepository.saveAll(itensEntity)
        return itemMapper.toDomain(itensSalvos)
    }
}