package tech.challenge.orderservice.helpers

import tech.challenge.orderservice.infrastructure.db.entity.ItemEntity
import tech.challenge.orderservice.infrastructure.db.entity.PedidoEntity
import java.util.*

class ItemEntityHelper {

    companion object{

        fun gerarItem(): ItemEntity {
            return ItemEntity().apply {
                id = UUID.randomUUID()
                produto = ProdutoEntityHelper.gerarProduto()
                pedido = PedidoEntityHelper.gerarPedidoComItensVazio()
                observacoes = "Sem salada!"
                quantidade = 1
            }
        }

        fun gerarItem(pedidoEntity: PedidoEntity): ItemEntity {
            return ItemEntity().apply {
                id = UUID.randomUUID()
                produto = ProdutoEntityHelper.gerarProduto()
                pedido = pedidoEntity
                observacoes = "Sem salada!"
                quantidade = 1
            }
        }

        fun gerarListItens(pedido: PedidoEntity): MutableList<ItemEntity> {
            return mutableListOf(gerarItem(pedido))
        }

        fun gerarListItens(): MutableList<ItemEntity> {
            return mutableListOf(gerarItem())
        }
    }
}