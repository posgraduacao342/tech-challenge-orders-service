package tech.challenge.orderservice.infrastructure.db.entity

import jakarta.persistence.*
import tech.challenge.orderservice.domain.enums.Categoria
import java.math.BigDecimal

@Entity
@Table(name = "produtos")
data class ProdutoEntity(
        @Column(nullable = false)
        var nome: String? = null,

        @Column(nullable = false)
        var preco: BigDecimal? = null,

        var imagem: String? = null,

        var descricao: String? = null,

        @Enumerated(EnumType.STRING)
        var categoria: Categoria? = null,

        ) : BaseEntity()
