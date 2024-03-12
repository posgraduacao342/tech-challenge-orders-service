package tech.challenge.orderservice.helpers

import tech.challenge.orderservice.domain.enums.StatusPedido
import tech.challenge.orderservice.infrastructure.db.entity.PedidoEntity
import java.time.LocalDateTime
import java.util.*

class PedidoEntityHelper {

    companion object{

        fun gerarPedido(): PedidoEntity {
            return PedidoEntity().apply {
                statusPedido = StatusPedido.CRIADO
                itens = ItemEntityHelper.gerarListItens()
                preco = 10.00.toBigDecimal()
                dataRecebimento = LocalDateTime.now()
                clienteId = UUID.randomUUID()
                id = UUID.randomUUID()
            }
        }

        fun gerarPedidoComItensVazio(): PedidoEntity {
            return PedidoEntity().apply {
                statusPedido = StatusPedido.CRIADO
                itens = mutableListOf()
                preco = 10.00.toBigDecimal()
                dataRecebimento = LocalDateTime.now()
                clienteId = UUID.randomUUID()
                id = UUID.randomUUID()
            }
        }

        fun gerarListPedidos(): MutableList<PedidoEntity> {
            return mutableListOf(gerarPedido(), gerarPedido())
        }
    }
}