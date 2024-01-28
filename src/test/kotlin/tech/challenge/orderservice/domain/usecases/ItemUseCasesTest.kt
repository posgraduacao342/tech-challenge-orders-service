package tech.challenge.orderservice.domain.usecases

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import tech.challenge.orderservice.domain.ports.out.ItemGatewayPort
import tech.challenge.orderservice.helpers.ItemHelper
import java.util.*

class ItemUseCasesTest {

    @Mock
    private lateinit var itemGateway: ItemGatewayPort

    @InjectMocks
    private lateinit var itemUseCases: ItemUseCases

    var openMocks: AutoCloseable? = null

    @BeforeEach
    fun setUp() {
        openMocks = MockitoAnnotations.openMocks(this)
    }

    @Test
    fun buscarItensPorPedido_DeveRetornarUmaListaDeItens() {
        // Given
        val pedidoId = UUID.randomUUID()
        val itemsList = ItemHelper.gerarListItens()

        // Mocking the behavior of itemGateway.buscarItensPorPedido
        `when`(itemGateway.buscarItensPorPedido(pedidoId)).thenReturn(itemsList)

        // When
        val result = itemUseCases.buscarItensPorPedido(pedidoId)

        // Then
        assert(result == itemsList)
    }

    @Test
    fun buscarItem_DeveRetornarUmItem() {
        val pedidoId = UUID.randomUUID()
        val item = ItemHelper.gerarIten()

        // Mocking the behavior of itemGateway.buscarItensPorPedido
        `when`(itemGateway.buscarItem(pedidoId)).thenReturn(item)

        // When
        val result = itemUseCases.buscarItem(pedidoId)

        // Then
        assert(result == item)
    }

    @Test
    fun atualizarObservacao_DeveRetornarItemAtualizado(){
        val itemId = UUID.randomUUID()
        val observacao = "Sem carne"
        val updatedItem = ItemHelper.gerarIten()
        updatedItem.observacoes = observacao

        `when`(itemGateway.atualizarObervacoes(itemId, observacao)).thenReturn(updatedItem)

        val result = itemUseCases.atualizarObservacao(itemId, observacao)

        assert(result == updatedItem)
    }

}