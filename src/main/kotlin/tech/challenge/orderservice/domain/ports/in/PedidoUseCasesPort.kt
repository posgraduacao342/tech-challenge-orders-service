package tech.challenge.orderservice.domain.ports.`in`

import tech.challenge.orderservice.domain.enums.PedidoSortingOptions
import java.util.*
import org.springframework.data.domain.Sort
import tech.challenge.orderservice.domain.entities.Pedido
import tech.challenge.orderservice.domain.enums.StatusPedido
import tech.challenge.orderservice.domain.exception.RecursoNaoEncontradoException

interface PedidoUseCasesPort {
    fun buscarPedidos(sortingProperty: Optional<PedidoSortingOptions>, direction: Optional<Sort.Direction>): List<Pedido>

    @Throws(RecursoNaoEncontradoException::class)
    fun buscarPedidoPorId(id: UUID): Pedido

    fun salvarPedido(pedido: Pedido): Pedido

    @Throws(RecursoNaoEncontradoException::class)
    fun atualizarStatusPedido(statusPedido: StatusPedido, id: UUID): Pedido

    fun deletarPedido(id: UUID)
}