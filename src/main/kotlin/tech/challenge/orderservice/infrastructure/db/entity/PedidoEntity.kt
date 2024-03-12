package tech.challenge.orderservice.infrastructure.db.entity

import jakarta.persistence.*
import tech.challenge.orderservice.domain.enums.MetodoPagamento
import tech.challenge.orderservice.domain.enums.StatusPagamento
import tech.challenge.orderservice.domain.enums.StatusPedido
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "pedidos")
data class PedidoEntity(
    @Column
    @Enumerated(EnumType.STRING)
    var statusPedido: StatusPedido?,

    @Column
    @Enumerated(EnumType.STRING)
    var statusPagamento: StatusPagamento?,

    @OneToMany(mappedBy = "pedido", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var itens: MutableList<ItemEntity> = mutableListOf(),

    @Column(nullable = false)
    var preco: BigDecimal?,

    @Column
    var dataRecebimento: LocalDateTime?,

    @Column
    var clienteId: UUID?,

    @Column(nullable = false)
    var metodoPagamento: MetodoPagamento?

    ) : BaseEntity() {

    override fun toString(): String {
        return "PedidoEntity{id=${super.id}}"
    }

    constructor() : this(
        statusPedido = null,
        statusPagamento = null,
        itens = mutableListOf(),
        preco = null,
        dataRecebimento = null,
        clienteId = null,
        metodoPagamento = null
    )

    fun adicionarItem(item: ItemEntity) {
        val listaItem = this.itens.toMutableList()
        listaItem.add(item)
        item.pedido = this
        this.itens = listaItem
    }
}