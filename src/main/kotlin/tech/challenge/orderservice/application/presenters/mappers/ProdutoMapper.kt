package tech.challenge.orderservice.application.presenters.mappers

import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Component
import tech.challenge.orderservice.application.presenters.requests.produto.AtualizarProdutoRequest
import tech.challenge.orderservice.application.presenters.requests.produto.CriarProdutoRequest
import tech.challenge.orderservice.domain.entities.Produto
import tech.challenge.orderservice.infrastructure.db.entity.ProdutoEntity
import java.time.LocalDateTime
import java.time.ZoneId

@Component
class ProdutoMapper {

    fun toDomain(produtoEntity: ProdutoEntity): Produto {
        val produto = Produto()
        BeanUtils.copyProperties(produtoEntity, produto)
        return produto
    }

    fun toDomainWithId(produtoEntity: ProdutoEntity): Produto {
        val produto = Produto()
        BeanUtils.copyProperties(produtoEntity, produto)
        produto.id = produtoEntity.id
        return produto
    }

    fun toDomainList(produtoEntityList: List<ProdutoEntity>): List<Produto> {
        return produtoEntityList.map { toDomain(it) }
    }

    fun toDomain(request: CriarProdutoRequest): Produto {
        val produto = Produto()
        BeanUtils.copyProperties(request, produto)
        return produto
    }

    fun toDomain(produtoEntities: List<ProdutoEntity>): List<Produto> {
        return produtoEntities.map { toDomain(it) }
    }

    fun toDomain(request: AtualizarProdutoRequest, produto: Produto): Produto {
        request.nome?.let { produto.nome = it }
        request.preco?.let { produto.preco = it }
        request.imagem?.let { produto.imagem = it }
        request.descricao?.let { produto.descricao = it }
        request.categoria?.let { produto.categoria = it }
        produto.dataAtualizacao = LocalDateTime.now(ZoneId.of("UTC"))
        return produto
    }

    fun toEntity(produto: Produto): ProdutoEntity {
        val produtoEntity = ProdutoEntity()
        BeanUtils.copyProperties(produto, produtoEntity)
        produtoEntity.dataCriacao = LocalDateTime.now(ZoneId.of("UTC"))
        produtoEntity.dataAtualizacao = LocalDateTime.now(ZoneId.of("UTC"))
        return produtoEntity
    }
}