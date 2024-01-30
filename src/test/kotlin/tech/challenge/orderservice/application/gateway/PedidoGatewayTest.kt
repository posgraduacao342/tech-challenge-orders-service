package tech.challenge.orderservice.application.gateway

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.data.domain.Sort
import tech.challenge.orderservice.application.presenters.mappers.PedidoMapper
import tech.challenge.orderservice.domain.enums.PedidoSortingOptions
import tech.challenge.orderservice.domain.enums.StatusPedido
import tech.challenge.orderservice.helpers.PedidoEntityHelper
import tech.challenge.orderservice.helpers.PedidoHelper
import tech.challenge.orderservice.infrastructure.db.repositories.PedidoRepository
import java.util.*

class PedidoGatewayTest {

    @Mock
    private lateinit var pedidoRepository: PedidoRepository

    @Mock
    private lateinit var pedidoMapper: PedidoMapper

    @InjectMocks
    private lateinit var pedidoGateway: PedidoGateway

    var openMocks: AutoCloseable? = null

    @BeforeEach
    fun setUp() {
        openMocks = MockitoAnnotations.openMocks(this)
    }

    @Test
    fun buscarPedidos_DeveRetornarPedidos() {
        val pedidoEntities = PedidoEntityHelper.gerarListPedidos()
        val pedidos = PedidoHelper.gerarListPedidos()

        `when`(pedidoRepository.findAll()).thenReturn(pedidoEntities)
        `when`(pedidoMapper.toDomain(pedidoEntities[0])).thenReturn(pedidos[0])
        `when`(pedidoMapper.toDomain(pedidoEntities[1])).thenReturn(pedidos[1])

        val result = pedidoGateway.buscarPedidos(Optional.empty(), Optional.empty())

        verify(pedidoRepository).findAll()
        verify(pedidoMapper).toDomain(pedidoEntities[0])
        verify(pedidoMapper).toDomain(pedidoEntities[1])

        Assertions.assertEquals(pedidos, result)
    }

    @Test
    fun buscarPedidosPorStatusPedido_DeveRetornarPedidos() {
        val statusPedidoList = listOf(StatusPedido.PRONTO, StatusPedido.EM_PREPARACAO)
        val pedidoEntities = PedidoEntityHelper.gerarListPedidos()
        val pedidos = PedidoHelper.gerarListPedidos()
        val sort = Sort.by(Sort.Direction.ASC, "dataPedido")

        `when`(pedidoRepository.findByStatusPedidoIn(statusPedidoList, sort)).thenReturn(pedidoEntities)
        `when`(pedidoMapper.toDomain(pedidoEntities[0])).thenReturn(pedidos[0])
        `when`(pedidoMapper.toDomain(pedidoEntities[1])).thenReturn(pedidos[1])

        val result = pedidoGateway.buscarPedidosPorStatusPedido(statusPedidoList, sort)

        verify(pedidoRepository).findByStatusPedidoIn(statusPedidoList, sort)
        verify(pedidoMapper).toDomain(pedidoEntities[0])
        verify(pedidoMapper).toDomain(pedidoEntities[1])

        Assertions.assertEquals(pedidos, result)
    }

    @Test
    fun buscarPedidosPorStatusPedido_DeveRetornarPedidosOrdenados() {
        val statusPedidoList = listOf(StatusPedido.PRONTO, StatusPedido.EM_PREPARACAO)
        val sortingProperty = PedidoSortingOptions.DATA_RECEBIMENTO
        val direction = Sort.Direction.ASC
        val sort = Sort.by(direction, sortingProperty.string)

        val pedidoEntities = PedidoEntityHelper.gerarListPedidos()
        val pedidos = PedidoHelper.gerarListPedidos()

        `when`(pedidoRepository.findByStatusPedidoIn(statusPedidoList, sort)).thenReturn(pedidoEntities)
        `when`(pedidoMapper.toDomain(pedidoEntities[0])).thenReturn(pedidos[0])
        `when`(pedidoMapper.toDomain(pedidoEntities[1])).thenReturn(pedidos[1])

        val result = pedidoGateway.buscarPedidosPorStatusPedido(statusPedidoList, sortingProperty, direction)

        verify(pedidoRepository).findByStatusPedidoIn(statusPedidoList, sort)
        verify(pedidoMapper).toDomain(pedidoEntities[0])
        verify(pedidoMapper).toDomain(pedidoEntities[1])
        Assertions.assertEquals(pedidos, result)
    }

    @Test
    fun buscarPedidoPorId_DeveRetornarPedido() {
        val pedidoId = UUID.randomUUID()
        val pedidoEntity = PedidoEntityHelper.gerarPedido()
        val pedido = PedidoHelper.gerarPedido()

        `when`(pedidoRepository.findById(pedidoId)).thenReturn(Optional.of(pedidoEntity))
        `when`(pedidoMapper.toDomain(pedidoEntity)).thenReturn(pedido)

        val result = pedidoGateway.buscarPedidoPorId(pedidoId)

        verify(pedidoRepository).findById(pedidoId)
        verify(pedidoMapper).toDomain(pedidoEntity)

        Assertions.assertTrue(result.isPresent)
        Assertions.assertEquals(pedido, result.get())
    }

    @Test
    fun salvarPedido_DeveSalvarPedidoERetornar() {
        val pedido = PedidoHelper.gerarPedido()
        val pedidoEntity = PedidoEntityHelper.gerarPedido()

        `when`(pedidoMapper.toEntity(pedido)).thenReturn(pedidoEntity)
        `when`(pedidoRepository.save(pedidoEntity)).thenReturn(pedidoEntity)
        `when`(pedidoMapper.toDomain(pedidoEntity)).thenReturn(pedido)

        val result = pedidoGateway.salvarPedido(pedido)

        verify(pedidoMapper, times(1)).toEntity((pedido))
        verify(pedidoRepository, times(1)).save((pedidoEntity))
        verify(pedidoMapper, times(1)).toDomain((pedidoEntity))
        Assertions.assertEquals(pedido, result)
    }

    @Test
    fun deletarPedido_DeveDeletarUmPedido() {
        val pedidoId = UUID.randomUUID()

        pedidoGateway.deletarPedido(pedidoId)

        verify(pedidoRepository, times(1)).deleteById(eq(pedidoId))
    }
}