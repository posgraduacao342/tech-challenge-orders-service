package tech.challenge.orderservice.application.gateway

import jakarta.transaction.Transactional
import org.springframework.stereotype.Component
import tech.challenge.orderservice.application.presenters.mappers.PedidoMapper
import tech.challenge.orderservice.domain.enums.PedidoSortingOptions
import org.springframework.data.domain.Sort
import tech.challenge.orderservice.domain.entities.Pedido
import tech.challenge.orderservice.domain.enums.StatusPedido
import tech.challenge.orderservice.domain.exception.RecursoNaoEncontradoException
import tech.challenge.orderservice.domain.ports.out.PedidoAdapterPort
import tech.challenge.orderservice.infrastructure.db.entity.PedidoEntity
import tech.challenge.orderservice.infrastructure.db.repositories.PedidoRepository
import java.text.MessageFormat
import java.util.*
import kotlin.collections.ArrayList

@Component
class PedidoGateway(
    private val pedidoRepository: PedidoRepository,
    private val pedidoMapper: PedidoMapper
) : PedidoAdapterPort {

    @Transactional
    override fun buscarPedidos(
        sortingProperty: Optional<PedidoSortingOptions>,
        direction: Optional<Sort.Direction>
    ): List<Pedido> {
        val pedidosEntity: List<PedidoEntity>

        pedidosEntity = if (sortingProperty.isPresent) {
            pedidoRepository.findAll(
                Sort.by(
                    direction.orElse(Sort.Direction.ASC),
                    sortingProperty.get().string
                )
            )
        } else {
            pedidoRepository.findAll()
        }

        val pedidos = ArrayList<Pedido>()

        for (pedidoEntity in pedidosEntity) {
            pedidos.add(pedidoMapper.toDomain(pedidoEntity)!!)
        }
        return pedidos
    }

    @Transactional
    override fun buscarPedidosPorStatusPedido(
        statusPedidoList: List<StatusPedido>,
        sort: Sort
    ): List<Pedido> {
        val pedidosEntity = pedidoRepository.findByStatusPedidoIn(statusPedidoList, sort)

        val pedidos = ArrayList<Pedido>()

        for (pedidoEntity in pedidosEntity) {
            pedidos.add(pedidoMapper.toDomain(pedidoEntity)!!)
        }
        return pedidos
    }

    @Transactional
    override fun buscarPedidosPorStatusPedido(
        statusPedidoList: List<StatusPedido>,
        sortingProperty: PedidoSortingOptions,
        direction: Sort.Direction
    ): List<Pedido> {
        val sort = Sort.by(direction, sortingProperty.string)
        return buscarPedidosPorStatusPedido(statusPedidoList, sort)
    }

    @Transactional
    @Throws(RecursoNaoEncontradoException::class)
    override fun buscarPedidoPorId(id: UUID): Optional<Pedido> {
        val pedidoEntity = pedidoRepository.findById(id)
            .orElseThrow { RecursoNaoEncontradoException(MessageFormat.format("Registro não encontrado com código {0}", id)) }

        return Optional.of(pedidoMapper.toDomain(pedidoEntity)!!)
    }

    @Transactional
    override fun salvarPedido(pedido: Pedido): Pedido {
        val pedidoEntity = pedidoMapper.toEntity(pedido)
        return pedidoMapper.toDomain(pedidoRepository.save(pedidoEntity))!!
    }

    @Transactional
    override fun deletarPedido(id: UUID) {
        pedidoRepository.deleteById(id)
    }
}