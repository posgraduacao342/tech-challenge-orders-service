package tech.challenge.orderservice.infrastructure.db.messaging.`in`

import com.google.gson.Gson
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Service
import tech.challenge.orderservice.domain.enums.StatusPedido
import tech.challenge.orderservice.domain.ports.`in`.PedidoQueuePort
import tech.challenge.orderservice.domain.usecases.PedidoUseCases
import java.util.UUID


@Service
class PedidoQueueAdapterIN(
    private val pedidoUseCases: PedidoUseCases,
    @Autowired private val gson: Gson
) : PedidoQueuePort {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @RabbitListener(queues = ["\${queue1.name}"])
    override fun receive(@Payload message: String) {
        //val mensagem = gson.fromJson<HashMap<String, String>>(message, HashMap::class.java)
        val id = UUID.fromString(message)
        val status = StatusPedido.CANCELADO
       pedidoUseCases.atualizarStatusPedido(status, id)
    }
}