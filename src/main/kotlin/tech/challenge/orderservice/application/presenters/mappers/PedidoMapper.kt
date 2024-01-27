package tech.challenge.orderservice.application.presenters.mappers

import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Component
import tech.challenge.orderservice.application.presenters.requests.pedido.CriarPedidoRequest
import tech.challenge.orderservice.application.presenters.responses.item.ItemResponse
import tech.challenge.orderservice.application.presenters.responses.pedido.PedidoResponse
import tech.challenge.orderservice.domain.entities.Item
import tech.challenge.orderservice.domain.entities.Pedido
import tech.challenge.orderservice.domain.entities.Produto
import tech.challenge.orderservice.infrastructure.db.entity.ItemEntity
import tech.challenge.orderservice.infrastructure.db.entity.PedidoEntity
import tech.challenge.orderservice.infrastructure.db.entity.ProdutoEntity

@Component
class PedidoMapper(
        private val genericMapper: GenericMapper
) {
    fun toDomain(pedidoRequest: CriarPedidoRequest): Pedido {
        val pedido = Pedido()

        pedidoRequest.itens?.forEach { itensRequest ->
            val item = Item()
            val produto = Produto()
            produto.id = itensRequest.produtoId
            BeanUtils.copyProperties(itensRequest, item)
            item.produto = produto
            pedido.adicionarItem(item)
        }

        BeanUtils.copyProperties(pedidoRequest, pedido)

        return pedido
    }

    fun toDomain(pedidoEntity: PedidoEntity): Pedido {
        val pedido = genericMapper.toTransform(pedidoEntity, Pedido::class.java)
        pedidoEntity.itens.forEach { itemEntity ->
            val item = genericMapper.toTransform(itemEntity, Item::class.java)
            val produto = genericMapper.toTransform(itemEntity.produto, Produto::class.java)
            item?.produto = produto
            if (item != null) {
                pedido?.adicionarItem(item)
            }
        }

        return pedido!!
    }

    fun toEntity(pedido: Pedido): PedidoEntity {
        val pedidoEntity = PedidoEntity()

        BeanUtils.copyProperties(pedido, pedidoEntity)

        pedido.itens.forEach { item ->
            val itemEntity = ItemEntity()
            BeanUtils.copyProperties(item, itemEntity)

            val produtoEntity = ProdutoEntity()
            produtoEntity.id = item.produto?.id
            itemEntity.produto = produtoEntity

            itemEntity.pedido = pedidoEntity

            pedidoEntity.adicionarItem(itemEntity)
        }

        return pedidoEntity
    }

    fun toResponse(pedido: Pedido): PedidoResponse {
        val pedidoResponse = PedidoResponse()

        pedido.itens.forEach { item ->
            val itemResponse = ItemResponse()
            BeanUtils.copyProperties(item, itemResponse)
            pedidoResponse.adicionarItem(itemResponse)
        }

        if(pedido.idCliente != null) {
            pedidoResponse.clienteId = pedido.id
        }

        BeanUtils.copyProperties(pedido, pedidoResponse)
        return pedidoResponse
    }

    fun toResponse(pedidoList: List<Pedido>): List<PedidoResponse> {
        return pedidoList.map { toResponse(it) }
    }
}