package tech.challenge.orderservice.infrastructure.db.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "itens")
class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_produto")
    var produto: ProdutoEntity? = null

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    var pedido: PedidoEntity? = null

    @Column(name = "observacoes")
    var observacoes: String? = null

    @Column(name = "quantidade")
    var quantidade: Int = 0
}