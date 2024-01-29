package tech.challenge.orderservice.helpers

import tech.challenge.orderservice.domain.entities.Pedido
import tech.challenge.orderservice.domain.enums.StatusPagamento
import tech.challenge.orderservice.domain.enums.StatusPedido
import java.time.LocalDateTime
import java.util.*

class PedidoHelper {

    companion object {
        fun gerarPedido(): Pedido {
            return Pedido(
                idCliente = UUID.randomUUID(),
                statusPedido = StatusPedido.EM_PREPARACAO,
                preco = 10.00.toBigDecimal(),
                statusPagamento = StatusPagamento.AGUARDANDO_PAGAMENTO,
                itens = ItemHelper.listaItemPadrao(),
                dataRecebimento = LocalDateTime.now()
            )
        }

        fun gerarPedidoComItensVazio(): Pedido {
            return Pedido(
                idCliente = UUID.randomUUID(),
                statusPedido = StatusPedido.EM_PREPARACAO,
                preco = 10.00.toBigDecimal(),
                statusPagamento = StatusPagamento.AGUARDANDO_PAGAMENTO,
                itens = mutableListOf(),
                dataRecebimento = LocalDateTime.now()
            )
        }

        fun gerarListPedidos(): List<Pedido> {
            return mutableListOf(gerarPedido(), gerarPedido())
        }
    }
}