package tech.challenge.orderservice.domain.ports.out

import tech.challenge.orderservice.domain.enums.PedidoSortingOptions
import org.springframework.data.domain.Sort
import tech.challenge.orderservice.domain.entities.Pedido
import tech.challenge.orderservice.domain.enums.StatusPedido
import java.util.*

interface PedidoGatewayPort {
    fun buscarPedidos(sortingProperty: Optional<PedidoSortingOptions>, direction: Optional<Sort.Direction>): List<Pedido>

    fun buscarPedidosPorStatusPedido(statusPedidoList: List<StatusPedido>, sort: Sort): List<Pedido>

    fun buscarPedidoPorId(id: UUID): Pedido?

    fun salvarPedido(pedido: Pedido): Pedido

    fun deletarPedido(id: UUID)
}
