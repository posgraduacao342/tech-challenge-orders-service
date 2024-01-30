package tech.challenge.orderservice.application.presenters.requests.item

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

class ItemRequestTest {

    @Test
    fun itensRequestValoresPadrao() {
        val itemRequest = ItensRequest()

        Assertions.assertEquals(null, itemRequest.produtoId)
        Assertions.assertEquals(null, itemRequest.observacoes)
        Assertions.assertEquals(null, itemRequest.quantidade)
    }

    @Test
    fun itensRequestAtribuindoValores() {
        val produtoId = UUID.randomUUID()
        val observacoes = "Observaçao teste"
        val quantidade = 100

        val itensRequest = ItensRequest(
            produtoId = produtoId,
            observacoes = observacoes,
            quantidade = quantidade,
        )

        Assertions.assertEquals(produtoId, itensRequest.produtoId)
        Assertions.assertEquals(observacoes, itensRequest.observacoes)
        Assertions.assertEquals(quantidade, itensRequest.quantidade)
    }

    @Test
    fun itensRequestModificandoValores() {
        val id = UUID.randomUUID()
        val itemRequest = ItensRequest()

        val novoItensRquest = itemRequest.copy(
            produtoId = id,
            observacoes = "Observaçao teste",
            quantidade = 100,
        )

        Assertions.assertEquals(id, novoItensRquest.produtoId)
        Assertions.assertEquals("Observaçao teste", novoItensRquest.observacoes)
        Assertions.assertEquals(100, novoItensRquest.quantidade)
    }

}