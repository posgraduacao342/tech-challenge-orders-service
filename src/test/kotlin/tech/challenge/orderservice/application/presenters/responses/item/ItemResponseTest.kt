package tech.challenge.orderservice.application.presenters.responses.item

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import tech.challenge.orderservice.application.presenters.mappers.ItemMapper
import java.util.*

class ItemResponseTest {
    @Mock
    private lateinit var itemMapper: ItemMapper

    @InjectMocks
    private lateinit var itemResponse: ItemResponse

    @Test
    fun criarItemResponseEVerificarPropriedades() {
        // Given
        val itemId = UUID.randomUUID()
        val observacoes = "Observações de teste"
        val quantidade = 42

        // When
        val itemResponse = ItemResponse(id = itemId, observacoes = observacoes, quantidade = quantidade)

        // Then
        Assertions.assertEquals(itemId, itemResponse.id)
        Assertions.assertEquals(observacoes, itemResponse.observacoes)
        Assertions.assertEquals(quantidade, itemResponse.quantidade)
    }

    @Test
    fun criarItemResponseComValorPadrao() {
        // When
        val itemResponse = ItemResponse()

        // Then
        Assertions.assertEquals(null, itemResponse.id)
        Assertions.assertEquals(null, itemResponse.observacoes)
        Assertions.assertEquals(null, itemResponse.quantidade)
    }

    @Test
    fun alterarItemResponse() {
        // Given
        val itemResponse = ItemResponse()

        // When
        val newItemResponse = itemResponse.copy(
            id = UUID.randomUUID(),
            observacoes = "Nova observação",
            quantidade = 99
        )

        // Then
        Assertions.assertNotEquals(newItemResponse.id, itemResponse.id)
        Assertions.assertNotEquals(newItemResponse.observacoes, itemResponse.observacoes)
        Assertions.assertNotEquals(newItemResponse.quantidade, itemResponse.quantidade)
    }
}