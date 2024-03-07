package tech.challenge.orderservice.domain.ports.`in`

import org.springframework.messaging.handler.annotation.Payload
import org.springframework.amqp.support.AmqpHeaders
import org.springframework.messaging.handler.annotation.Header

interface PagamentoQueueAdapterINPort {
    fun receivePagamentoAprovado(@Payload message: String, @Header(AmqpHeaders.DELIVERY_TAG) deliveryTag: Long)
    fun receivePagamentoEstornado(@Payload message: String, @Header(AmqpHeaders.DELIVERY_TAG) deliveryTag: Long)
    fun receivePagamentoNaoAprovado(@Payload message: String, @Header(AmqpHeaders.DELIVERY_TAG) deliveryTag: Long)
}