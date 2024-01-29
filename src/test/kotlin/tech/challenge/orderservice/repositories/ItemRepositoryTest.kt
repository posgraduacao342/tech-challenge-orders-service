package tech.challenge.orderservice.repositories

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import tech.challenge.orderservice.helpers.ItemEntityHelper
import tech.challenge.orderservice.infrastructure.db.repositories.ItemRepository
import java.util.*

class ItemRepositoryTest {

    @Mock
    private lateinit var itemRepository: ItemRepository

    var openMocks: AutoCloseable? = null

    @BeforeEach
    fun setUp() {
        openMocks = MockitoAnnotations.openMocks(this)
    }

    @Test
    fun findByPedidoId_DeveRetornarListaDeItems() {
        // Given
        val pedidoId = UUID.randomUUID()
        val itemsList = ItemEntityHelper.gerarListItens()
        itemsList[0].pedido?.id = pedidoId
        val id = itemsList[0].pedido?.id

        itemRepository.saveAll(itemsList)

        `when`(itemRepository.findByPedidoId(id)).thenReturn(itemsList)

        // When
        val result = itemRepository.findByPedidoId(pedidoId)

        // Then
        assert(result.size == 1)
        assert(result.all { it.pedido?.id == pedidoId })
    }

    @Test
    fun `findByIdIn should return a list of items for given ids`() {
        // Given
        val pedidoId = UUID.randomUUID()
        val itemsList = ItemEntityHelper.gerarListItens()
        itemsList[0].pedido?.id = pedidoId

        itemRepository.saveAll(itemsList)

        // Mocking the behavior of itemRepository.findByIdIn
        `when`(itemRepository.findByIdIn(itemsList.map { it.id })).thenReturn(itemsList)

        // When
        val result = itemRepository.findByIdIn(itemsList.map { it.id })

        // Then
        Assertions.assertEquals(1, result.size)
        Assertions.assertTrue { result.containsAll(itemsList) }
    }
}