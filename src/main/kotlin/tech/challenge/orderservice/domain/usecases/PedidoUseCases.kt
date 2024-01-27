package tech.challenge.orderservice.domain.usecases

import tech.challenge.orderservice.domain.enums.PedidoSortingOptions
import java.util.*
import org.springframework.data.domain.Sort
import tech.challenge.orderservice.domain.entities.Pedido
import tech.challenge.orderservice.domain.enums.StatusPagamento
import tech.challenge.orderservice.domain.enums.StatusPedido
import tech.challenge.orderservice.domain.exception.AtributoInvalidoException
import tech.challenge.orderservice.domain.exception.RecursoNaoEncontradoException
import tech.challenge.orderservice.domain.ports.`in`.PedidoUseCasesPort
import tech.challenge.orderservice.domain.ports.out.PedidoAdapterPort
import tech.challenge.orderservice.domain.ports.out.ProdutoAdapterPort
import java.time.LocalDateTime
import java.time.ZoneId

class PedidoUseCases(
        private val pedidoAdapterPort: PedidoAdapterPort,
        private val produtoAdapterPort: ProdutoAdapterPort
) : PedidoUseCasesPort {

    override fun buscarPedidos(
            sortingProperty: Optional<PedidoSortingOptions>,
            direction: Optional<Sort.Direction>
    ): List<Pedido> {
        return pedidoAdapterPort.buscarPedidos(sortingProperty, direction)
    }

    override fun buscarFilaDePedidos(): List<Pedido> {
        val statusPedidoList = listOf(StatusPedido.PRONTO, StatusPedido.EM_PREPARACAO, StatusPedido.RECEBIDO)

        val orders = listOf(Sort.Order(Sort.Direction.DESC, PedidoSortingOptions.DATA_RECEBIMENTO.string))

        val pedidos = pedidoAdapterPort.buscarPedidosPorStatusPedido(statusPedidoList, Sort.by(orders))
        return ordenarPedidosPorStatus(pedidos)
    }

    private fun ordenarPedidosPorStatus(pedidos: List<Pedido>): List<Pedido> {
        return pedidos.sortedBy { it.statusPedido }
    }

    override fun buscarPedidoPorId(id: UUID): Pedido {
        return pedidoAdapterPort.buscarPedidoPorId(id)
                .orElseThrow { RecursoNaoEncontradoException("Registro não encontrado com código $id") }
    }

    override fun salvarPedido(pedido: Pedido): Pedido {
        pedido.dataCriacao = LocalDateTime.now(ZoneId.of("UTC"))
        pedido.dataAtualizacao = LocalDateTime.now(ZoneId.of("UTC"))

        if (pedido.statusPagamento == null) {
            pedido.statusPagamento = StatusPagamento.AGUARDANDO_PAGAMENTO
        }
        if (pedido.statusPedido == null) {
            pedido.statusPedido = StatusPedido.NAO_RECEBIDO
        }

        val ids = pedido.itens.map { it.produto?.id }
        val produtos = produtoAdapterPort.buscarProdutoPorIds(ids)

        if (produtos.size != pedido.itens.size) {
            throw RecursoNaoEncontradoException("Produto informado não existe!")
        }

        for (item in pedido.itens) {
            for (produto in produtos) {
                if (item.produto?.id == produto.id) {
                    item.produto = produto
                }
            }
        }

        if (!pedido.validarPreco()) {
            throw AtributoInvalidoException("Preço informado está incorreto!")
        }

        return pedidoAdapterPort.salvarPedido(pedido)
    }

    override fun atualizarStatusPedido(statusPedido: StatusPedido, id: UUID): Pedido {
        val pedido = buscarPedidoPorId(id)
        pedido.dataAtualizacao = LocalDateTime.now(ZoneId.of("UTC"))
        pedido.statusPedido = statusPedido
        return pedidoAdapterPort.salvarPedido(pedido)
    }

    override fun deletarPedido(id: UUID) {
        pedidoAdapterPort.deletarPedido(id)
    }
}