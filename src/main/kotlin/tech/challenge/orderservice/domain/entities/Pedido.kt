package tech.challenge.orderservice.domain.entities

import tech.challenge.orderservice.domain.enums.MetodoPagamento
import tech.challenge.orderservice.domain.enums.StatusPagamento
import tech.challenge.orderservice.domain.enums.StatusPedido
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class Pedido(
    var idCliente: UUID? = null,
    var statusPedido: StatusPedido? = null,
    var preco: BigDecimal? = null,
    var statusPagamento: StatusPagamento? = null,
    var itens: MutableList<Item> = mutableListOf(),
    var dataRecebimento: LocalDateTime? = null,
    var metodoPagamento: MetodoPagamento? = null

    ) : BaseEntity() {

    init {
        this.itens = mutableListOf()
    }

    fun adicionarItem(item: Item) {
        val listaItem = this.itens.toMutableList()
        listaItem.add(item)
        this.itens = listaItem
    }

    fun pagamentoRealizado(): Boolean {
        return this.statusPagamento == StatusPagamento.PAGO
    }

    fun atualizarStatusPedido(statusPagamento: StatusPagamento) {
        if (statusPagamento == StatusPagamento.PAGO) {
            this.statusPedido = StatusPedido.CRIADO
        }
        this.statusPagamento = statusPagamento
        this.dataRecebimento = LocalDateTime.now()
    }

    fun validarPreco(): Boolean {
        val totalItens = itens.fold(BigDecimal.ZERO) { acc, item -> acc + item.calcularTotal() }
        return preco?.compareTo(totalItens) == 0
    }
}
