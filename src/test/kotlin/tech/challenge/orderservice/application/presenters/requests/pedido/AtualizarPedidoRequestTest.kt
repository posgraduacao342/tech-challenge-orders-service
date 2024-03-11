package tech.challenge.orderservice.application.presenters.requests.pedido

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import tech.challenge.orderservice.domain.enums.StatusPedido

class AtualizarPedidoRequestTest {

    @Test
    fun atualizarPedidoRequestValoresPadrao() {
        val atualizarPedidoRequest = AtualizarPedidoRequest()

        Assertions.assertEquals(null, atualizarPedidoRequest.statusPedido)
    }

    @Test
    fun atualizarPedidoRequestAtribuindoValores() {
        val statusPedido = StatusPedido.CRIADO

        // When
        val atualizarPedidoRequest = AtualizarPedidoRequest(
            statusPedido = statusPedido
        )

        // Then
        Assertions.assertEquals(statusPedido, atualizarPedidoRequest.statusPedido)
    }

    @Test
    fun atualizarPedidoRequestModificandoValores() {
        val atualizarPedidoRequest = AtualizarPedidoRequest()

        val novaAtualizarPedidoRequest = atualizarPedidoRequest.copy(
            statusPedido = StatusPedido.CRIADO
        )

        Assertions.assertEquals(StatusPedido.CRIADO, novaAtualizarPedidoRequest.statusPedido)
    }
}