package tech.challenge.orderservice.application.presenters.requests.item

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

class CriarItemRequestTest {

    @Test
    fun criarItensRequestValoresPadrao() {
        val itemRequest = CriarItemRequest()

        Assertions.assertEquals(null, itemRequest.pedidoId)
        Assertions.assertEquals(null, itemRequest.produtoId)
        Assertions.assertEquals(null, itemRequest.observacoes)
        Assertions.assertEquals(0, itemRequest.quantidade)
    }

    @Test
    fun criarItensRequestAtribuindoValores() {
        val produtoId = UUID.randomUUID()
        val pedidoId = UUID.randomUUID()
        val observacoes = "Observaçao teste"
        val quantidade = 100

        val criarItensRequest = CriarItemRequest(
            pedidoId = pedidoId,
            produtoId = produtoId,
            observacoes = observacoes,
            quantidade = quantidade,
        )

        Assertions.assertEquals(pedidoId, criarItensRequest.pedidoId)
        Assertions.assertEquals(produtoId, criarItensRequest.produtoId)
        Assertions.assertEquals(observacoes, criarItensRequest.observacoes)
        Assertions.assertEquals(quantidade, criarItensRequest.quantidade)
    }

    @Test
    fun criarItensRequestModificandoValores() {
        val pedidoId = UUID.randomUUID()
        val produtoId = UUID.randomUUID()
        val criarItensRequest = CriarItemRequest()

        val novoCriarItensRequest = criarItensRequest.copy(
            pedidoId = pedidoId,
            produtoId = produtoId,
            observacoes = "Observaçao teste",
            quantidade = 100,
        )

        Assertions.assertEquals(pedidoId, novoCriarItensRequest.pedidoId)
        Assertions.assertEquals(produtoId, novoCriarItensRequest.produtoId)
        Assertions.assertEquals("Observaçao teste", novoCriarItensRequest.observacoes)
        Assertions.assertEquals(100, novoCriarItensRequest.quantidade)
    }
}