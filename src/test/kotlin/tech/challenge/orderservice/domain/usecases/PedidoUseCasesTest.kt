package tech.challenge.orderservice.domain.usecases

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.data.domain.Sort
import tech.challenge.orderservice.domain.enums.PedidoSortingOptions
import tech.challenge.orderservice.domain.enums.StatusPagamento
import tech.challenge.orderservice.domain.enums.StatusPedido
import tech.challenge.orderservice.domain.exception.AtributoInvalidoException
import tech.challenge.orderservice.domain.exception.RecursoNaoEncontradoException
import tech.challenge.orderservice.domain.ports.out.PedidoGatewayPort
import tech.challenge.orderservice.domain.ports.out.ProdutoGatewayPort
import tech.challenge.orderservice.helpers.PedidoHelper
import tech.challenge.orderservice.helpers.ProdutoHelper
import java.util.*

class PedidoUseCasesTest {

    @Mock
    private lateinit var pedidoGatewayPort: PedidoGatewayPort

    @Mock
    private lateinit var produtoGatewayPort: ProdutoGatewayPort

    @InjectMocks
    private lateinit var pedidoUseCases: PedidoUseCases

    var openMocks: AutoCloseable? = null

    @BeforeEach
    fun setUp() {
        openMocks = MockitoAnnotations.openMocks(this)
    }

    @Test
    fun salvarPedido() {
        val pedido = PedidoHelper.gerarPedidoPrecoZerado()
        val produto = ProdutoHelper.gerarProduto()

        `when`(produtoGatewayPort.buscarProdutoPorIds(listOf(pedido.id))).thenReturn(listOf(produto))
        `when`(pedidoGatewayPort.salvarPedido(pedido)).thenReturn(pedido)

        val result = pedidoUseCases.salvarPedido(pedido)

        Assertions.assertEquals(StatusPagamento.AGUARDANDO_PAGAMENTO, result.statusPagamento)
        Assertions.assertEquals(StatusPedido.EM_PREPARACAO, result.statusPedido)
    }

    @Test
    fun salvarPedido_DeveLancarRecursoNaoEncontradoException() {
        val pedido = PedidoHelper.gerarPedido()

        `when`(produtoGatewayPort.buscarProdutoPorIds(listOf(pedido.id))).thenReturn(emptyList())

        assertThrows<AtributoInvalidoException> {
            pedidoUseCases.salvarPedido(pedido)
        }
    }

    @Test
    fun buscarPedidos_DeveRetornarListaDePedidos() {
        // Given
        val sortingProperty = Optional.of(PedidoSortingOptions.DATA_RECEBIMENTO)
        val direction = Optional.of(Sort.Direction.ASC)
        val pedidos = PedidoHelper.gerarListPedidos()

        // Mocking the behavior of pedidoGatewayPort.buscarPedidos
        `when`(pedidoGatewayPort.buscarPedidos(sortingProperty, direction)).thenReturn(pedidos)

        // When
        val result = pedidoUseCases.buscarPedidos(sortingProperty, direction)

        // Then
        assert(result == pedidos)
    }

    @Test
    fun buscarFilaDePedidos_DeveRetornarListaDePedidosOrdenada() {
        // Given
        val statusPedidoList = listOf(StatusPedido.PRONTO, StatusPedido.EM_PREPARACAO, StatusPedido.RECEBIDO)
        val pedidos = PedidoHelper.gerarListPedidos()
        pedidos[0].id = UUID.randomUUID()
        pedidos[1].id = UUID.randomUUID()
        pedidos[0].statusPedido = StatusPedido.PRONTO
        pedidos[1].statusPedido = StatusPedido.EM_PREPARACAO

        // Mocking the behavior of pedidoGatewayPort.buscarPedidosPorStatusPedido
        `when`(pedidoGatewayPort.buscarPedidosPorStatusPedido(statusPedidoList, Sort.by(Sort.Order(Sort.Direction.DESC, PedidoSortingOptions.DATA_RECEBIMENTO.string))))
            .thenReturn(pedidos)

        // When
        val result = pedidoUseCases.buscarFilaDePedidos()

        // Then
        val expectedSortedPedidos = pedidos.sortedBy { it.dataRecebimento }
        assert(result == expectedSortedPedidos)
    }

    @Test
    fun buscarPedidoPorId_DeveRetornarOPedido() {
        val pedidoId = UUID.randomUUID()
        val pedido = PedidoHelper.gerarPedido()

        `when`(pedidoGatewayPort.buscarPedidoPorId(pedidoId)).thenReturn(Optional.of(pedido))

        val result = pedidoUseCases.buscarPedidoPorId(pedidoId)

        assert(result == pedido)
    }

    @Test
    fun buscarPedidoPorId_DeveRetornarErroRecursoNaoEncontradoException() {
        val pedidoId = UUID.randomUUID()

        `when`(pedidoGatewayPort.buscarPedidoPorId(pedidoId)).thenReturn(Optional.empty())

        assertThrows<RecursoNaoEncontradoException> {
            pedidoUseCases.buscarPedidoPorId(pedidoId)
        }
    }

    @Test
    fun salvarPedido_DeveSalvarPedido() {
        val pedidoId = UUID.randomUUID()
        val pedido = PedidoHelper.gerarPedido()
        pedido.id = pedidoId

        `when`(pedidoGatewayPort.buscarPedidoPorId(pedidoId)).thenReturn(Optional.of(pedido))

        `when`(pedidoGatewayPort.salvarPedido(pedido)).thenReturn(pedido)

        val result = pedidoUseCases.atualizarStatusPedido(pedido.statusPedido!!, pedido.id!!)

        verify(pedidoGatewayPort).salvarPedido(pedido)
        assert(result == pedido)
    }

    @Test
    fun atualizarStatusPedido_DeveRetornarPedidoAtualizado() {
        // Given
        val pedidoId = UUID.randomUUID()
        val pedido = PedidoHelper.gerarPedido()
        pedido.id = pedidoId

        // Mocking the behavior of pedidoGatewayPort.buscarPedidoPorId
        `when`(pedidoGatewayPort.buscarPedidoPorId(pedidoId)).thenReturn(Optional.of(pedido))

        // Mocking the behavior of pedidoGatewayPort.salvarPedido
        `when`(pedidoGatewayPort.salvarPedido(pedido)).thenReturn(pedido)

        // When
        val result = pedidoUseCases.atualizarStatusPedido(pedido.statusPedido!!, pedido.id!!)

        // Then
        verify(pedidoGatewayPort).salvarPedido(pedido)
        assert(result == pedido)
    }

    @Test
    fun deletarPedido_DeveDeletarUmPedido() {
        // Given
        val pedidoId = UUID.randomUUID()

        // When
        pedidoUseCases.deletarPedido(pedidoId)

        // Then
        verify(pedidoGatewayPort).deletarPedido(pedidoId)
    }
}