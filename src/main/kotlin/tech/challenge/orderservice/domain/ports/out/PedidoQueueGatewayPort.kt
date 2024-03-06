package tech.challenge.orderservice.domain.ports.out

interface PedidoQueueGatewayPort {
    fun publish(message: String)
}