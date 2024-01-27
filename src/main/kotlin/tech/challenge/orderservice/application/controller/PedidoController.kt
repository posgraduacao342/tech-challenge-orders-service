package tech.challenge.orderservice.application.controller

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import tech.challenge.orderservice.application.presenters.mappers.PedidoMapper
import tech.challenge.orderservice.application.presenters.requests.pedido.CriarPedidoRequest
import tech.challenge.orderservice.application.presenters.responses.pedido.PedidoResponse
import tech.challenge.orderservice.domain.entities.Pedido
import org.springframework.data.domain.Sort
import tech.challenge.orderservice.application.presenters.requests.pedido.AtualizarPedidoRequest
import tech.challenge.orderservice.domain.enums.PedidoSortingOptions
import tech.challenge.orderservice.domain.ports.`in`.PedidoUseCasesPort
import java.util.*

@RestController
@RequestMapping("/pedidos")
class PedidoController(
    private val pedidoUseCases: PedidoUseCasesPort,
    private val pedidoMapper: PedidoMapper
) {

    @GetMapping
    fun buscarPedidos(
        @RequestParam(required = false) sortingProperty: PedidoSortingOptions?,
        @RequestParam(required = false) direction: Sort.Direction?
    ): List<Pedido> {
        return pedidoUseCases.buscarPedidos(Optional.ofNullable(sortingProperty), Optional.ofNullable(direction))
    }

    @GetMapping("/fila")
    fun buscarFilaDePedidos(): List<Pedido> {
        return pedidoUseCases.buscarFilaDePedidos()
    }

    @GetMapping("/{pedidoId}")
    fun buscarPedidoPorId(@PathVariable pedidoId: UUID): Pedido {
        return pedidoUseCases.buscarPedidoPorId(pedidoId)
    }

    @PostMapping
    fun salvarPedido(@RequestBody @Valid pedidoRequest: CriarPedidoRequest): PedidoResponse {
        val pedido = pedidoMapper.toDomain(pedidoRequest)
        return pedidoMapper.toResponse(pedidoUseCases.salvarPedido(pedido))
    }

    @PatchMapping("/{pedidoId}/status")
    fun atualizarPedido(
        @PathVariable pedidoId: UUID,
        @RequestBody @Valid atualizarPedidoRequest: AtualizarPedidoRequest
    ): PedidoResponse {
        return pedidoMapper.toResponse(
            pedidoUseCases.atualizarStatusPedido(
                atualizarPedidoRequest.statusPedido!!, pedidoId
            )
        )
    }

    @DeleteMapping("/{pedidoId}")
    fun deletar(@PathVariable pedidoId: UUID) {
        pedidoUseCases.deletarPedido(pedidoId)
    }
}