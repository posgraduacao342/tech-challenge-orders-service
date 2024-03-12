package tech.challenge.orderservice.helpers

import tech.challenge.orderservice.application.presenters.requests.pedido.CriarPedidoRequest
import tech.challenge.orderservice.domain.entities.Pedido
import tech.challenge.orderservice.domain.enums.MetodoPagamento
import tech.challenge.orderservice.domain.enums.StatusPagamento
import tech.challenge.orderservice.domain.enums.StatusPedido
import java.time.LocalDateTime
import java.util.*

class PedidoHelper {

    companion object {
        fun gerarPedido(): Pedido {
            return Pedido(
                clienteId = UUID.randomUUID(),
                statusPedido = StatusPedido.CRIADO,
                preco = 10.00.toBigDecimal(),
                statusPagamento = StatusPagamento.AGUARDANDO_PAGAMENTO,
                itens = ItemHelper.listaItemPadrao(),
                dataRecebimento = LocalDateTime.now(),
                metodoPagamento = MetodoPagamento.DINHEIRO
            )
        }

        fun gerarPedidoPrecoZerado(): Pedido {
            return Pedido(
                clienteId = UUID.randomUUID(),
                statusPedido = StatusPedido.CRIADO,
                preco = 0.toBigDecimal(),
                statusPagamento = StatusPagamento.AGUARDANDO_PAGAMENTO,
                itens = ItemHelper.listaItemPadrao(),
                dataRecebimento = LocalDateTime.now(),
                metodoPagamento = MetodoPagamento.DINHEIRO
            )
        }

        fun gerarPedidoComItensVazio(): Pedido {
            return Pedido(
                clienteId = UUID.randomUUID(),
                statusPedido = StatusPedido.CRIADO,
                preco = 10.00.toBigDecimal(),
                statusPagamento = StatusPagamento.AGUARDANDO_PAGAMENTO,
                itens = mutableListOf(),
                dataRecebimento = LocalDateTime.now(),
                metodoPagamento = MetodoPagamento.DINHEIRO
            )
        }

        fun gerarListPedidos(): List<Pedido> {
            return mutableListOf(gerarPedido(), gerarPedido())
        }

        fun gerarPedidoRequest(): CriarPedidoRequest{
            return CriarPedidoRequest(
                clienteId = null,
                preco = null,
                itens = null,
                metodoPagamento = null
            )
        }
    }
}