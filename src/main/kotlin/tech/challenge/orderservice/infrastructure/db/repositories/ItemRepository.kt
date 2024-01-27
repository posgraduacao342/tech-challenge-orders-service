package tech.challenge.orderservice.infrastructure.db.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tech.challenge.orderservice.infrastructure.db.entity.ItemEntity
import java.util.*

@Repository
interface ItemRepository : JpaRepository<ItemEntity, UUID> {
    fun findByPedidoId(pedidoId: UUID?): List<ItemEntity>
    fun findByIdIn(ids: List<UUID?>): List<ItemEntity>
}