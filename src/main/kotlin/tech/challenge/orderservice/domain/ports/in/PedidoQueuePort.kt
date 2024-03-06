package tech.challenge.orderservice.domain.ports.`in`

import org.springframework.messaging.handler.annotation.Payload

interface PedidoQueuePort {
    fun receive(@Payload message: String)
}