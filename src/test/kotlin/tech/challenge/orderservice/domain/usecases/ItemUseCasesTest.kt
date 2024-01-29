package tech.challenge.orderservice.domain.usecases

import org.junit.jupiter.api.Assertions
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
        val pedidoId = UUID.randomUUID()
        val itemsList = ItemHelper.gerarListItens()

        `when`(itemGateway.buscarItensPorPedido(pedidoId)).thenReturn(itemsList)

        val result = itemUseCases.buscarItensPorPedido(pedidoId)

        Assertions.assertEquals(result, itemsList)
    }

    @Test
    fun buscarItem_DeveRetornarUmItem() {
        val pedidoId = UUID.randomUUID()
        val item = ItemHelper.gerarItem()

        `when`(itemGateway.buscarItem(pedidoId)).thenReturn(item)

        val result = itemUseCases.buscarItem(pedidoId)

        Assertions.assertEquals(result, item)
    }

    @Test
    fun atualizarObservacao_DeveRetornarItemAtualizado(){
        val itemId = UUID.randomUUID()
        val observacao = "Sem carne"
        val updatedItem = ItemHelper.gerarItem()
        updatedItem.observacoes = observacao

        `when`(itemGateway.atualizarObervacoes(itemId, observacao)).thenReturn(updatedItem)

        val result = itemUseCases.atualizarObservacao(itemId, observacao)

        Assertions.assertEquals(result, updatedItem)
    }

}