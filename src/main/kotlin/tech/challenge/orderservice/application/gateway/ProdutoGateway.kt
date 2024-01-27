package tech.challenge.orderservice.application.gateway

import jakarta.transaction.Transactional
import org.springframework.stereotype.Component
import tech.challenge.orderservice.application.presenters.mappers.ProdutoMapper
import tech.challenge.orderservice.domain.entities.Produto
import tech.challenge.orderservice.domain.enums.Categoria
import tech.challenge.orderservice.domain.exception.RecursoNaoEncontradoException
import tech.challenge.orderservice.domain.ports.out.ProdutoGatewayPort
import tech.challenge.orderservice.infrastructure.db.repositories.ProdutoRepository
import java.util.Optional
import java.util.UUID
@Component
class ProdutoGateway(
    private val produtoRepository: ProdutoRepository,
    private val produtoMapper: ProdutoMapper
) : ProdutoGatewayPort {

    @Transactional
    override fun buscarProdutos(): List<Produto> {
        val produtosEntity = produtoRepository.findAll()
        val produtos = ArrayList<Produto>()

        for (produtoEntity in produtosEntity) {
            produtos.add(produtoMapper.toDomain(produtoEntity))
        }

        return produtos
    }

    @Transactional
    @Throws(RecursoNaoEncontradoException::class)
    override fun buscarProdutoPorId(id: UUID): Optional<Produto> {
        val produtoEntity = produtoRepository.findById(id)
            .orElseThrow { RecursoNaoEncontradoException("Registro não encontrado com código $id") }

        return Optional.of(produtoMapper.toDomain(produtoEntity))
    }

    override fun buscarProdutoPorIds(ids: List<UUID?>): List<Produto> {
        val produtoEntity = produtoRepository.findByIdIn(ids)
        return produtoMapper.toDomain(produtoEntity)
    }

    @Transactional
    override fun buscarProdutosPorCategoria(categoria: Categoria): List<Produto> {
        val produtoEntityList = produtoRepository.findByCategoria(categoria)
        return produtoMapper.toDomainList(produtoEntityList)
    }

    @Transactional
    override fun deletarProduto(id: UUID) {
        produtoRepository.deleteById(id)
    }

    @Transactional
    override fun salvarProduto(produto: Produto): Produto {
        val produtoEntity = produtoMapper.toEntity(produto)
        return produtoMapper.toDomain(produtoRepository.save(produtoEntity))
    }
}