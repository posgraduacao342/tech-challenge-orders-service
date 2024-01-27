package tech.challenge.orderservice.domain.ports.out

import tech.challenge.orderservice.domain.entities.Produto
import tech.challenge.orderservice.domain.enums.Categoria
import tech.challenge.orderservice.domain.exception.RecursoNaoEncontradoException
import java.util.*

interface ProdutoAdapterPort {
    fun buscarProdutos(): List<Produto>

    @Throws(RecursoNaoEncontradoException::class)
    fun buscarProdutoPorId(id: UUID): Optional<Produto>

    fun buscarProdutoPorIds(ids: List<UUID?>): List<Produto>

    fun buscarProdutosPorCategoria(categoria: Categoria): List<Produto>

    fun deletarProduto(id: UUID)

    fun salvarProduto(produto: Produto): Produto
}