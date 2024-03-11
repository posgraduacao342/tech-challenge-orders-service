package tech.challenge.orderservice.infrastructure.db.messaging.`in`

import com.google.gson.Gson
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.AmqpHeaders
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Service
import tech.challenge.orderservice.domain.enums.StatusPagamento
import tech.challenge.orderservice.domain.enums.StatusPedido
import tech.challenge.orderservice.domain.exception.RecursoNaoEncontradoException
import tech.challenge.orderservice.domain.ports.`in`.PagamentoQueueAdapterINPort
import tech.challenge.orderservice.domain.usecases.PedidoUseCases
import java.util.*


@Service
class PagamentoQueueAdapterIN(
    private val pedidoUseCases: PedidoUseCases,
    @Autowired private val gson: Gson,
    @Autowired private val rabbitTemplate: RabbitTemplate
) : PagamentoQueueAdapterINPort {
    private val logger = LoggerFactory.getLogger(this.javaClass)
    private val pagamentoAtualizado = "Atualizado status do pagamento!"
    private val pedidoAtualizado = "Atualizado status do pedido!"

    @RabbitListener(queues = ["order_pagamento_aprovado"])
    override fun receivePagamentoAprovado(@Payload message: String, @Header(AmqpHeaders.DELIVERY_TAG) deliveryTag: Long) {
        try {
            val mensagem = gson.fromJson<HashMap<String, String>>(message, HashMap::class.java)
            val id = UUID.fromString(mensagem["pedidoId"])
            val status = StatusPagamento.PAGO

            pedidoUseCases.atualizarStatusPagamento(status, id)
            logger.info(pagamentoAtualizado)

        } catch (ex: RecursoNaoEncontradoException){
            logger.error("${ex.message}")

            rabbitTemplate.execute { channel ->
                channel.basicReject(deliveryTag, false)
            }
        } catch (ex: Exception) {
            logger.error("Ocorreu um erro ao processar a mensagem: $message", ex)

            rabbitTemplate.execute { channel ->
                channel.basicReject(deliveryTag, false)
            }
        }
    }

    @RabbitListener(queues = ["order_pagamento_estornado"])
    @Throws(RecursoNaoEncontradoException::class)
    override fun receivePagamentoEstornado(@Payload message: String, @Header(AmqpHeaders.DELIVERY_TAG) deliveryTag: Long) {
        try {
            val mensagem = gson.fromJson<HashMap<String, String>>(message, HashMap::class.java)
            val id = UUID.fromString(mensagem["pedidoId"])
            val statusPedido =  StatusPedido.CANCELADO
            val statusPagamento = StatusPagamento.ESTORNADO

            pedidoUseCases.atualizarStatusPagamento(statusPagamento, id)
            logger.info(pagamentoAtualizado)

            pedidoUseCases.atualizarStatusPedido(statusPedido, id)
            logger.info(pedidoAtualizado)

        } catch (ex: RecursoNaoEncontradoException){
            logger.error("${ex.message}")

            rabbitTemplate.execute { channel ->
                channel.basicReject(deliveryTag, false)
            }

        } catch (ex: Exception) {
            logger.error("Ocorreu um erro ao processar a mensagem: $message", ex)

            rabbitTemplate.execute { channel ->
                channel.basicReject(deliveryTag, false)
            }
        }
    }

    @RabbitListener(queues = ["order_pagamento_nao_aprovado"])
    @Throws(RecursoNaoEncontradoException::class)
    override fun receivePagamentoNaoAprovado(@Payload message: String, @Header(AmqpHeaders.DELIVERY_TAG) deliveryTag: Long) {
        try {
            val mensagem = gson.fromJson<HashMap<String, String>>(message, HashMap::class.java)
            val id = UUID.fromString(mensagem["pedidoId"])
            val statusPedido = StatusPedido.CANCELADO
            val statusPagamento = StatusPagamento.FRACASSADO

            pedidoUseCases.atualizarStatusPagamento(statusPagamento, id)
            logger.info(pagamentoAtualizado)

            pedidoUseCases.atualizarStatusPedido(statusPedido, id)
            logger.info(pedidoAtualizado)

        } catch (ex: RecursoNaoEncontradoException){
            logger.error("${ex.message}")

            rabbitTemplate.execute { channel ->
                channel.basicReject(deliveryTag, false)
            }
        } catch (ex: Exception) {
            logger.error("Ocorreu um erro ao processar a mensagem: $message", ex)

            rabbitTemplate.execute { channel ->
                channel.basicReject(deliveryTag, false)
            }
        }
    }
}