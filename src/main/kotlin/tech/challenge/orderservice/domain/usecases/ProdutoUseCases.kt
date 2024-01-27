package tech.challenge.orderservice.domain.usecases

import tech.challenge.orderservice.application.presenters.mappers.ProdutoMapper
import tech.challenge.orderservice.application.presenters.requests.produto.AtualizarProdutoRequest
import tech.challenge.orderservice.domain.entities.Produto
import tech.challenge.orderservice.domain.enums.Categoria
import tech.challenge.orderservice.domain.exception.RecursoNaoEncontradoException
import tech.challenge.orderservice.domain.ports.`in`.ProdutoUseCasesPort
import tech.challenge.orderservice.domain.ports.out.ProdutoGatewayPort
import java.util.*

class ProdutoUseCases(
    private val produtoGatewayPort: ProdutoGatewayPort,
    private val produtoMapper: ProdutoMapper
) : ProdutoUseCasesPort {

    override fun buscarProdutos(): List<Produto> {
        return produtoGatewayPort.buscarProdutos()
    }

    @Throws(RecursoNaoEncontradoException::class)
    override fun buscarProdutoPorId(id: UUID): Produto {
        return produtoGatewayPort.buscarProdutoPorId(id)
                .orElseThrow { RecursoNaoEncontradoException("Registro não encontrado com código $id") }
    }

    override fun buscarProdutosPorCategoria(categoria: Categoria): List<Produto> {
        return produtoGatewayPort.buscarProdutosPorCategoria(categoria)
    }

    override fun atualizarProduto(id: UUID, request: AtualizarProdutoRequest): Produto {
        val produto = buscarProdutoPorId(id)
        produtoMapper.toDomain(request, produto)
        return produtoGatewayPort.salvarProduto(produto)
    }

    override fun deletarProduto(id: UUID) {
        produtoGatewayPort.deletarProduto(id)
    }

    override fun criarNovoProduto(produto: Produto): Produto {
        return produtoGatewayPort.salvarProduto(produto)
    }
}