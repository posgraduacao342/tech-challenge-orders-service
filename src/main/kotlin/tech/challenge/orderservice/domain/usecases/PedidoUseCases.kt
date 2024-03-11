package tech.challenge.orderservice.domain.usecases

import com.google.gson.GsonBuilder
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Sort
import tech.challenge.orderservice.application.presenters.mappers.PedidoQueueMapper
import tech.challenge.orderservice.domain.entities.Pedido
import tech.challenge.orderservice.domain.enums.PedidoSortingOptions
import tech.challenge.orderservice.domain.enums.StatusPagamento
import tech.challenge.orderservice.domain.enums.StatusPedido
import tech.challenge.orderservice.domain.exception.AtributoInvalidoException
import tech.challenge.orderservice.domain.exception.RecursoNaoEncontradoException
import tech.challenge.orderservice.domain.ports.`in`.PedidoUseCasesPort
import tech.challenge.orderservice.domain.ports.out.PedidoGatewayPort
import tech.challenge.orderservice.domain.ports.out.PagamentoQueueAdapterOUTPort
import tech.challenge.orderservice.domain.ports.out.ProdutoGatewayPort
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*


open class PedidoUseCases(
    private val pedidoGatewayPort: PedidoGatewayPort,
    private val produtoGatewayPort: ProdutoGatewayPort,
    private val pagamentoQueueAdapterOUTPort: PagamentoQueueAdapterOUTPort,
    private val pedidoQueueMapper: PedidoQueueMapper,
) : PedidoUseCasesPort {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun buscarPedidos(
            sortingProperty: Optional<PedidoSortingOptions>,
            direction: Optional<Sort.Direction>
    ): List<Pedido> {
        return pedidoGatewayPort.buscarPedidos(sortingProperty, direction)
    }

    override fun buscarPedidoPorId(id: UUID): Pedido {
        return pedidoGatewayPort.buscarPedidoPorId(id)
            ?: throw RecursoNaoEncontradoException("Registro não encontrado com código $id")
    }

    @Transactional
    override fun salvarPedido(pedido: Pedido): Pedido {
        pedido.dataCriacao = LocalDateTime.now(ZoneId.of("UTC"))
        pedido.dataAtualizacao = LocalDateTime.now(ZoneId.of("UTC"))

        if (pedido.statusPagamento == null) {
            pedido.statusPagamento = StatusPagamento.AGUARDANDO_PAGAMENTO
        }
        if (pedido.statusPedido == null) {
            pedido.statusPedido = StatusPedido.CRIADO
        }
        if (pedido.metodoPagamento == null) {
            throw AtributoInvalidoException("Metodo de pagamento deve ser preenchido!")
        }


        val ids = pedido.itens.map { it.produto?.id }
        val produtos = produtoGatewayPort.buscarProdutoPorIds(ids)

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

        val pedidoSalvo = pedidoGatewayPort.salvarPedido(pedido)

        val gson = GsonBuilder()
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeTypeAdapter())
            .create()
        val pedidoJson = gson.toJson(pedidoSalvo)
        pagamentoQueueAdapterOUTPort.publish(pedidoJson)

        return pedidoSalvo
    }

    override fun atualizarStatusPedido(statusPedido: StatusPedido, id: UUID): Pedido {
        val pedido = buscarPedidoPorId(id)
        pedido.dataAtualizacao = LocalDateTime.now(ZoneId.of("UTC"))
        pedido.statusPedido = statusPedido
        return pedidoGatewayPort.salvarPedido(pedido)
    }

    override fun atualizarStatusPagamento(statusPagamento: StatusPagamento, id: UUID): Pedido {
        val pedido = buscarPedidoPorId(id)
        pedido.dataAtualizacao = LocalDateTime.now(ZoneId.of("UTC"))
        pedido.statusPagamento = statusPagamento
        return pedidoGatewayPort.salvarPedido(pedido)
    }

    override fun deletarPedido(id: UUID) {
        pedidoGatewayPort.deletarPedido(id)
    }
}