package tech.challenge.orderservice.application.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import tech.challenge.orderservice.application.presenters.mappers.ItemMapper
import tech.challenge.orderservice.application.presenters.requests.item.AtualizarObservacaoItemRequest
import tech.challenge.orderservice.application.presenters.responses.item.ItemResponse
import tech.challenge.orderservice.domain.entities.Item
import tech.challenge.orderservice.domain.ports.`in`.ItemUseCasesPort
import java.util.*

@RestController
@RequestMapping("/itens")
class ItemController(
    private val itemUseCases: ItemUseCasesPort,
    private val itemMapper: ItemMapper
) {
    @GetMapping("/porPedido/{pedidoId}")
    fun buscarItensPorPedido(@PathVariable pedidoId: UUID): ResponseEntity<MutableList<Item>> {
        val items = itemUseCases.buscarItensPorPedido(pedidoId)
        return ResponseEntity.ok(items)
    }

    @GetMapping("/{itemId}")
    fun buscarItem(@PathVariable itemId: UUID): ResponseEntity<Item> {
        val item = itemUseCases.buscarItem(itemId)
        return ResponseEntity.ok(item)
    }

    @PatchMapping(value = ["/observacao"])
    fun atualizarObservacao(@RequestBody request: AtualizarObservacaoItemRequest): ItemResponse? {
        return itemMapper.toResponse(itemUseCases.atualizarObservacao(request.id!!, request.observacoes!!))
    }
}