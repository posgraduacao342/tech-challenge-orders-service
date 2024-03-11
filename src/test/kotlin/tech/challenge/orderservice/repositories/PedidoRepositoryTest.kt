package tech.challenge.orderservice.repositories

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import tech.challenge.orderservice.domain.enums.StatusPedido
import tech.challenge.orderservice.helpers.PedidoEntityHelper
import tech.challenge.orderservice.infrastructure.db.repositories.PedidoRepository
import org.springframework.data.domain.Sort

class PedidoRepositoryTest {

    @Mock
    private lateinit var pedidoRepository: PedidoRepository

    var openMocks: AutoCloseable? = null

    @BeforeEach
    fun setUp() {
        openMocks = MockitoAnnotations.openMocks(this)
    }

    @Test
    fun findByStatusPedidoIn_DeveRetornarListaDePedidos() {
        // Given
        val statusPedidoList = listOf(StatusPedido.CRIADO, StatusPedido.CRIADO, StatusPedido.CRIADO)
        val pedidos = PedidoEntityHelper.gerarListPedidos()

        pedidoRepository.saveAll(pedidos)

        Mockito.`when`(pedidoRepository.findByStatusPedidoIn(statusPedidoList, Sort.by(Sort.Order(Sort.Direction.DESC, "dataRecebimento"))))
            .thenReturn(pedidos)

        // When
        val result = pedidoRepository.findByStatusPedidoIn(statusPedidoList, Sort.by(Sort.Order(Sort.Direction.DESC, "dataRecebimento")))

        // Then
        val expectedSortedPedidos = pedidos.sortedBy { it.dataRecebimento }
        assert(result == expectedSortedPedidos)
    }
}