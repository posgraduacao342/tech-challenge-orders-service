package tech.challenge.orderservice.application.presenters.requests.pedido

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import tech.challenge.orderservice.application.presenters.requests.item.ItensRequest
import java.math.BigDecimal
import java.util.*

class CriarPedidoRequestTest {

    @Test
    fun criarPedidoRequestValoresPadrao() {
        val criarPedidoRequest = CriarPedidoRequest()

        Assertions.assertEquals(null, criarPedidoRequest.clienteId)
        Assertions.assertEquals(null, criarPedidoRequest.preco)
        Assertions.assertEquals(null, criarPedidoRequest.itens)
    }

    @Test
    fun criarPedidoRequestAtribuindoValores() {
        val idCliente = UUID.randomUUID()
        val preco = BigDecimal.valueOf(100.0)
        val itens = ItensRequest(produtoId =UUID.randomUUID(), observacoes = "Observacao", quantidade = 2)

        // When
        val atualizarPedidoRequest = CriarPedidoRequest(
            clienteId = idCliente,
            preco = preco,
            itens = mutableListOf(itens,itens)
        )

        // Then
        Assertions.assertEquals(idCliente, atualizarPedidoRequest.clienteId)
        Assertions.assertEquals(preco, atualizarPedidoRequest.preco)
        Assertions.assertEquals(2, atualizarPedidoRequest.itens!!.size)
        Assertions.assertEquals(itens, atualizarPedidoRequest.itens!![1])
    }

    @Test
    fun criarPedidoRequestModificandoValores() {
        val id = UUID.randomUUID()
        val itens = ItensRequest(produtoId =UUID.randomUUID(), observacoes = "Observacao", quantidade = 2)
        val criarPedidoRequest = CriarPedidoRequest()

        val novoCriarPedidoRequest = criarPedidoRequest.copy(
            clienteId = id,
            preco = BigDecimal.valueOf(100.0),
            itens = listOf(itens)
        )

        Assertions.assertEquals(id, novoCriarPedidoRequest.clienteId)
        Assertions.assertEquals(BigDecimal.valueOf(100.0), novoCriarPedidoRequest.preco)
        Assertions.assertEquals(listOf(itens), novoCriarPedidoRequest.itens)
    }
}