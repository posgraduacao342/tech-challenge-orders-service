package tech.challenge.orderservice.application.presenters.requests.item

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

class AtualizarObservacaoItemRequestTest {

    @Test
    fun atualizarObservacaoItemRequestValoresPadrao() {
        val atualizarObservacaoItemRequest = AtualizarObservacaoItemRequest()

        Assertions.assertEquals(null, atualizarObservacaoItemRequest.id)
        Assertions.assertEquals(null, atualizarObservacaoItemRequest.observacoes)
    }

    @Test
    fun atualizarObservacaoItemRequestAtribuindoValores() {
        val id = UUID.randomUUID()
        val observacoes = "observacao teste"

        val atualizarObservacaoItemRequest = AtualizarObservacaoItemRequest(
            id = id,
            observacoes = observacoes,
        )

        Assertions.assertEquals(id, atualizarObservacaoItemRequest.id)
        Assertions.assertEquals(observacoes, atualizarObservacaoItemRequest.observacoes)
    }

    @Test
    fun atualizarObservacaoItemRequestModificandoValores() {
        val id = UUID.randomUUID()
        val atualizarObservacaoItemRequest = AtualizarObservacaoItemRequest()

        val novoAtualizarObservacaoItemRequest = atualizarObservacaoItemRequest.copy(
            id = id,
            observacoes = "observacao teste",
        )

        Assertions.assertEquals(id, novoAtualizarObservacaoItemRequest.id)
        Assertions.assertEquals("observacao teste", novoAtualizarObservacaoItemRequest.observacoes)
    }
}