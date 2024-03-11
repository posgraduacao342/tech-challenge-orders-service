package tech.challenge.orderservice.domain.ports.out

interface PagamentoQueueAdapterOUTPort {
    fun publish(message: String)
}