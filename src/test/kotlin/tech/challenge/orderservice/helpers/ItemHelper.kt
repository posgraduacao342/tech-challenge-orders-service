package tech.challenge.orderservice.helpers

import tech.challenge.orderservice.domain.entities.Item
import java.util.*

class ItemHelper {

    companion object {
        fun gerarIten(): Item {
            return Item(
                id = UUID.randomUUID(),
                produto = ProdutoHelper.gerarProduto(),
                pedido = PedidoHelper.gerarPedidoComItensVazio(),
                observacoes = "Sem salada!",
                quantidade = 1
            )
        }

        fun gerarListItens(): MutableList<Item> {
            return mutableListOf(gerarIten())
        }
    }
}