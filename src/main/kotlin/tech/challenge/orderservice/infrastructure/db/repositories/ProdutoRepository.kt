package tech.challenge.orderservice.infrastructure.db.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tech.challenge.orderservice.domain.enums.Categoria
import tech.challenge.orderservice.infrastructure.db.entity.ProdutoEntity
import java.util.*

@Repository
interface ProdutoRepository : JpaRepository<ProdutoEntity, UUID> {
    fun findByCategoria(categoria: Categoria): List<ProdutoEntity>
    fun findByIdIn(ids: List<UUID?>): List<ProdutoEntity>
}