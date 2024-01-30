package tech.challenge.orderservice.application.presenters.responses.pedido

import org.junit.jupiter.api.Test
import tech.challenge.orderservice.application.presenters.responses.item.ItemResponse
import org.junit.jupiter.api.Assertions
import tech.challenge.orderservice.domain.enums.StatusPagamento
import tech.challenge.orderservice.domain.enums.StatusPedido
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

class PedidoResponseTest {

    @Test
    fun adicionarItem_DeveAdicionarItem() {
        val pedidoResponse = PedidoResponse()
        val item = ItemResponse(id = UUID.randomUUID(), observacoes = "Observacao", quantidade = 2)

        pedidoResponse.adicionarItem(item)

        Assertions.assertEquals(1, pedidoResponse.itens.size)
        Assertions.assertEquals(item, pedidoResponse.itens[0])
    }

    @Test
    fun pedidoResponseValoresPadrao() {
        // When
        val pedidoResponse = PedidoResponse()

        // Then
        Assertions.assertEquals(null, pedidoResponse.id)
        Assertions.assertEquals(null, pedidoResponse.clienteId)
        Assertions.assertEquals(null, pedidoResponse.dataCriacao)
        Assertions.assertEquals(null, pedidoResponse.dataDelecao)
        Assertions.assertEquals(null, pedidoResponse.dataAtualizacao)
        Assertions.assertEquals(null, pedidoResponse.statusPedido)
        Assertions.assertEquals(null, pedidoResponse.preco)
        Assertions.assertEquals(null, pedidoResponse.statusPagamento)
        Assertions.assertEquals(0, pedidoResponse.itens.size)
    }

    @Test
    fun pedidoResponseAtribuindoValores() {
        val id = UUID.randomUUID()
        val clienteId = UUID.randomUUID()
        val dataCriacao = LocalDateTime.now()
        val dataDelecao = LocalDateTime.now()
        val dataAtualizacao = LocalDateTime.now()
        val statusPedido = StatusPedido.EM_PREPARACAO
        val preco = BigDecimal.valueOf(100.0)
        val statusPagamento = StatusPagamento.PAGO
        val item = ItemResponse(id = UUID.randomUUID(), observacoes = "Observacao", quantidade = 2)

        val pedidoResponse = PedidoResponse(
            id = id,
            clienteId = clienteId,
            dataCriacao = dataCriacao,
            dataDelecao = dataDelecao,
            dataAtualizacao = dataAtualizacao,
            statusPedido = statusPedido,
            preco = preco,
            statusPagamento = statusPagamento,
            itens = mutableListOf(item,item)
        )

        Assertions.assertEquals(id, pedidoResponse.id)
        Assertions.assertEquals(clienteId, pedidoResponse.clienteId)
        Assertions.assertEquals(dataCriacao, pedidoResponse.dataCriacao)
        Assertions.assertEquals(dataDelecao, pedidoResponse.dataDelecao)
        Assertions.assertEquals(dataAtualizacao, pedidoResponse.dataAtualizacao)
        Assertions.assertEquals(statusPedido, pedidoResponse.statusPedido)
        Assertions.assertEquals(preco, pedidoResponse.preco)
        Assertions.assertEquals(statusPagamento, pedidoResponse.statusPagamento)
        Assertions.assertEquals(2, pedidoResponse.itens.size)
        Assertions.assertEquals(item, pedidoResponse.itens[0])
    }
}