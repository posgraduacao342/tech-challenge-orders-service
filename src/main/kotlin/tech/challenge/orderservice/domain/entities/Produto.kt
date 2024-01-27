package tech.challenge.orderservice.domain.entities

import tech.challenge.orderservice.domain.enums.Categoria
import java.math.BigDecimal

data class Produto(
        var nome: String? = null,
        var preco: BigDecimal? = null,
        var imagem: String? = null,
        var descricao: String? = null,
        var categoria: Categoria? = null,
) : BaseEntity()
