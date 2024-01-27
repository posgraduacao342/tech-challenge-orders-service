package tech.challenge.orderservice.infrastructure.db.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.data.domain.Sort;
import tech.challenge.orderservice.domain.enums.StatusPedido
import tech.challenge.orderservice.infrastructure.db.entity.PedidoEntity
import java.util.*

@Repository
interface PedidoRepository : JpaRepository<PedidoEntity, UUID> {
    fun findByStatusPedidoIn(statusPedido: List<StatusPedido>, sort: Sort): List<PedidoEntity>
    fun findByStatusPedidoIn(statusPedido: List<StatusPedido>): List<PedidoEntity>
}