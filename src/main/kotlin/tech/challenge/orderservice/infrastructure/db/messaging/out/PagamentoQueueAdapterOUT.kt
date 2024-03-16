package tech.challenge.orderservice.infrastructure.db.messaging.out

import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import tech.challenge.orderservice.application.config.RabbitMQConfig
import tech.challenge.orderservice.domain.ports.out.PagamentoQueueAdapterOUTPort

@Service
class PagamentoQueueAdapterOUT(
    @Autowired private val rabbitTemplate: RabbitTemplate,
    @Value(RabbitMQConfig.NOVO_PEDIDO_ROUTING_KEY) private val routingKey: String,
    @Value(RabbitMQConfig.AMQ_DIRECT_EXCHANGE) private val exchange: String,
): PagamentoQueueAdapterOUTPort {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun publish(message: String) {
        rabbitTemplate.convertAndSend(exchange,routingKey, message)
        logger.info("Publicado na fila com sucesso!")
    }
}