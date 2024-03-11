package tech.challenge.orderservice.infrastructure.db.messaging.out

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.springframework.amqp.rabbit.core.RabbitTemplate

class PagamentoQueueAdapterOUTTest {

    @Mock
    private lateinit var rabbitTemplate: RabbitTemplate

    private lateinit var routingKey: String

    private lateinit var exchange: String

    private lateinit var pagamentoQueueAdapterOUT: PagamentoQueueAdapterOUT

    var openMocks: AutoCloseable? = null

    @BeforeEach
    fun setUp() {
        openMocks = MockitoAnnotations.openMocks(this)
        routingKey = "novo.pedido"
        exchange = "amq.direct"
        pagamentoQueueAdapterOUT = PagamentoQueueAdapterOUT(rabbitTemplate, routingKey, exchange)
    }

    @Test
    fun `test publish`() {
        val message = """{"pedidoId": "f47ac10b-58cc-4372-a567-0e02b2c3d479"}"""

        pagamentoQueueAdapterOUT.publish(message)

        verify(rabbitTemplate).convertAndSend(exchange, routingKey, message)
    }
}