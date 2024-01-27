package tech.challenge.orderservice.domain.entities

import java.math.BigDecimal
import java.util.*

data class Item(
        var id: UUID? = null,
        var produto: Produto? = null,
        var pedido: Pedido? = null,
        var observacoes: String? = null,
        var quantidade: Int = 0
) {
    fun calcularTotal(): BigDecimal {
        val quantidadeDecimal = BigDecimal.valueOf(quantidade.toLong())
        val valorUnitario = produto?.preco ?: BigDecimal.ZERO
        val total = valorUnitario.multiply(quantidadeDecimal)

        return total
    }
}
