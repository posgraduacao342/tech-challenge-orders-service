package tech.challenge.orderservice.application.presenters.mappers

import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Component
import tech.challenge.orderservice.application.presenters.requests.item.CriarItemRequest
import tech.challenge.orderservice.application.presenters.responses.item.ItemResponse
import tech.challenge.orderservice.domain.entities.Item
import tech.challenge.orderservice.domain.entities.Pedido
import tech.challenge.orderservice.domain.entities.Produto
import tech.challenge.orderservice.infrastructure.db.entity.ItemEntity
import tech.challenge.orderservice.infrastructure.db.entity.ProdutoEntity

@Component
class ItemMapper(
        private val produtoMapper: ProdutoMapper,
        private val itemGenericMapper: GenericMapper
) {

    fun toEntity(item: Item?): ItemEntity? {
        val itemEntity = itemGenericMapper.toTransform(item, ItemEntity::class.java)
        itemEntity?.produto = itemGenericMapper.toTransform(item?.produto, ProdutoEntity::class.java)

        return itemEntity
    }

    fun toDomain(itemEntity: ItemEntity?): Item? {
        val item = itemGenericMapper.toTransform(itemEntity, Item::class.java)
        item?.produto = itemGenericMapper.toTransform(itemEntity?.produto, Produto::class.java)
        item?.pedido = itemGenericMapper.toTransform(itemEntity?.pedido, Pedido::class.java)

        return item
    }

    fun toDomain(request: CriarItemRequest): Item {
        val item = Item()
        val produto = Produto()
        val pedido = Pedido()

        produto.id = request.produtoId
        pedido.id = request.pedidoId

        item.produto = produto
        item.observacoes = request.observacoes
        item.quantidade = request.quantidade

        return item
    }

    fun toDomain(itensEntity: List<ItemEntity>): List<Item> {
        val itens = mutableListOf<Item>()
        itensEntity.forEach { itemEntity ->
            toDomain(itemEntity)?.let { itens.add(it) }
        }
        return itens
    }

    fun toEntity(itens: List<Item?>): List<ItemEntity> {
        val itensEntity = mutableListOf<ItemEntity>()
        itens.forEach { item ->
            toEntity(item)?.let { itensEntity.add(it) }
        }
        return itensEntity
    }

    fun toResponse(item: Item): ItemResponse {
        val itemResponse = ItemResponse()
        BeanUtils.copyProperties(item, itemResponse)
        return itemResponse
    }
}