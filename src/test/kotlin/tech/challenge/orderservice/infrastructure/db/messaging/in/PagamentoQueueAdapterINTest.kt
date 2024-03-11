package tech.challenge.orderservice.infrastructure.db.messaging.`in`

import com.google.gson.Gson
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.amqp.rabbit.core.RabbitTemplate
import tech.challenge.orderservice.domain.enums.StatusPagamento
import tech.challenge.orderservice.domain.exception.RecursoNaoEncontradoException
import tech.challenge.orderservice.domain.usecases.PedidoUseCases
import tech.challenge.orderservice.helpers.PedidoHelper
import java.util.*

class PagamentoQueueAdapterINTest {

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var rabbitTemplate: RabbitTemplate

    @Mock
    private lateinit var pedidoUseCases: PedidoUseCases

    @InjectMocks
    private lateinit var pagamentoQueueAdapterIN: PagamentoQueueAdapterIN

    var openMocks: AutoCloseable? = null

    @BeforeEach
    fun setUp() {
        openMocks = MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `test receivePagamentoAprovado`() {
        val pedido = PedidoHelper.gerarPedido()
        val message = """{"pedidoId": "f47ac10b-58cc-4372-a567-0e02b2c3d479"}"""
        val mensagem = hashMapOf("pedidoId" to "f47ac10b-58cc-4372-a567-0e02b2c3d479")
        val id = UUID.fromString(mensagem["pedidoId"])
        val status = StatusPagamento.PAGO
        val deliveryTag = 1L

        pedido.statusPagamento = status
        pedido.id = id

        `when`(gson.fromJson<HashMap<String, String>>(message, HashMap::class.java)).thenReturn(mensagem)
        `when`(pedidoUseCases.atualizarStatusPagamento(status, id)).thenReturn(pedido)

        pagamentoQueueAdapterIN.receivePagamentoAprovado(message, deliveryTag)

        verify(pedidoUseCases).atualizarStatusPagamento(status, id)
    }

    @Test
    fun `test receivePagamentoAprovado with RecursoNaoEncontradoException`() {
        val message = """{"pedidoId": "f47ac10b-58cc-4372-a567-0e02b2c3d479"}"""
        val mensagem = hashMapOf("pedidoId" to "f47ac10b-58cc-4372-a567-0e02b2c3d479")
        val id = UUID.fromString(mensagem["pedidoId"])
        val status = StatusPagamento.PAGO
        val deliveryTag = 1L

        `when`(gson.fromJson<HashMap<String, String>>(message, HashMap::class.java)).thenReturn(mensagem)
        doThrow(RecursoNaoEncontradoException("Registro não encontrado com código $id")).`when`(pedidoUseCases)
            .atualizarStatusPagamento(status, id)

        pagamentoQueueAdapterIN.receivePagamentoAprovado(message, deliveryTag)

        verify(pedidoUseCases).atualizarStatusPagamento(status, id)
    }

    @Test
    fun `test receivePagamentoAprovado with unexpected exception`() {
        val message = """{"pedidoId":"123e4567-e89b-12d3-a456-556642440000"}"""
        val mensagem = hashMapOf("pedidoId" to "f47ac10b-58cc-4372-a567-0e02b2c3d479")
        val id = UUID.fromString(mensagem["pedidoId"])
        val status = StatusPagamento.PAGO
        val deliveryTag = 1L

        `when`(gson.fromJson<HashMap<String, String>>(message, HashMap::class.java)).thenReturn(mensagem)
        doThrow(RuntimeException("Ocorreu um erro ao processar a mensagem: $message")).`when`(pedidoUseCases)
            .atualizarStatusPagamento(status, id)

        pagamentoQueueAdapterIN.receivePagamentoAprovado(message, deliveryTag)

        verify(pedidoUseCases).atualizarStatusPagamento(status, id)
    }
}