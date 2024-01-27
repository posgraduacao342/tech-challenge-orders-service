package tech.challenge.orderservice.domain.ports.`in`

import tech.challenge.orderservice.application.presenters.requests.produto.AtualizarProdutoRequest
import tech.challenge.orderservice.domain.entities.Produto
import tech.challenge.orderservice.domain.enums.Categoria
import tech.challenge.orderservice.domain.exception.RecursoNaoEncontradoException
import java.util.*

interface ProdutoUseCasesPort {
    fun buscarProdutos(): List<Produto>

    @Throws(RecursoNaoEncontradoException::class)
    fun buscarProdutoPorId(id: UUID): Produto

    fun buscarProdutosPorCategoria(categoria: Categoria): List<Produto>

    fun atualizarProduto(id: UUID, produto: AtualizarProdutoRequest): Produto

    fun deletarProduto(id: UUID)

    fun criarNovoProduto(produto: Produto): Produto
}