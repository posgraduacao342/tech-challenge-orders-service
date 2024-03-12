package tech.challenge.orderservice.application.presenters.mappers

import com.google.gson.Gson
import org.springframework.stereotype.Component
import tech.challenge.orderservice.domain.entities.Pedido
import java.util.HashMap

@Component
class PedidoQueueMapper {
    fun toPedidoMessage(pedido: Pedido): String {
        val message = HashMap<String, String>()
        message["idCliente"] = pedido.clienteId.toString()
        message["statusPedido"] = pedido.statusPedido.toString()
        message["preco"] = pedido.preco.toString()
        message["statusPagamento"] = pedido.statusPagamento.toString()
        message["itens"] = pedido.itens.toString()
        message["dataRecebimento"] = pedido.dataRecebimento.toString()
        val gson = Gson()
        return gson.toJson(message)
    }
}