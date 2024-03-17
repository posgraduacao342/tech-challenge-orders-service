package tech.challenge.orderservice.application.config

import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class RabbitMQConfig {

    companion object {
        const val AMQ_DIRECT_EXCHANGE: String = "amq.direct"
        const val ORDER_PAGAMENTO_APROVADO: String = "order_pagamento_aprovado"
        const val ORDER_PAGAMENTO_APROVADO_ROUTING_KEY: String = "pagamento.aprovado"
        const val ORDER_PAGAMENTO_ESTORNADO: String = "order_pagamento_estornado"
        const val ORDER_PAGAMENTO_ESTORNADO_ROUTING_KEY: String = "pagamento.estornado"
        const val ORDER_PAGAMENTO_NAO_APROVADO: String = "order_pagamento_nao_aprovado"
        const val ORDER_PAGAMENTO_NAO_APROVADO_ROUTING_KEY: String = "pagamento.rejeitado"
        const val NOVO_PEDIDO_ROUTING_KEY: String = "novo.pedido"
    }

    @Bean
    fun exchange(): DirectExchange {
        return DirectExchange(AMQ_DIRECT_EXCHANGE)
    }

    @Bean
    fun pagamentoAprovadoQueue(): Queue {
        return Queue(ORDER_PAGAMENTO_APROVADO)
    }

    @Bean
    fun pagamentoAprovadoBinding(): Binding {
        return BindingBuilder.bind(pagamentoAprovadoQueue()).to(exchange()).with(ORDER_PAGAMENTO_APROVADO_ROUTING_KEY)
    }

    @Bean
    fun pagamentoEstornadoQueue(): Queue {
        return Queue(ORDER_PAGAMENTO_ESTORNADO)
    }

    @Bean
    fun pagamentoEstornadoBinding(): Binding {
        return BindingBuilder.bind(pagamentoEstornadoQueue()).to(exchange()).with(ORDER_PAGAMENTO_ESTORNADO_ROUTING_KEY)
    }

    @Bean
    fun pagamentoNaoAprovadoQueue(): Queue {
        return Queue(ORDER_PAGAMENTO_NAO_APROVADO)
    }

    @Bean
    fun pagamentoNaoAprovadoQueueBinding(): Binding {
        return BindingBuilder.bind(pagamentoEstornadoQueue()).to(exchange())
            .with(ORDER_PAGAMENTO_NAO_APROVADO_ROUTING_KEY)
    }

    @Bean
    fun amqpAdmin(connectionFactory: ConnectionFactory): AmqpAdmin {
        return RabbitAdmin(connectionFactory)
    }

}
