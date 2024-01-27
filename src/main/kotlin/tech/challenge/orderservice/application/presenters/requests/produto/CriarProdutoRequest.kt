package tech.challenge.orderservice.application.presenters.requests.produto

import jakarta.validation.constraints.NotBlank
import tech.challenge.orderservice.domain.enums.Categoria
import java.math.BigDecimal

data class CriarProdutoRequest(
        @field:NotBlank
        var nome: String? = null,
        var preco: BigDecimal? = null,
        var imagem: String? = null,
        var descricao: String? = null,
        var categoria: Categoria? = null,
)
