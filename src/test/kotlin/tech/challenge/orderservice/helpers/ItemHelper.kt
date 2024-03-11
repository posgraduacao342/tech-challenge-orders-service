package tech.challenge.orderservice.helpers

import tech.challenge.orderservice.domain.entities.Item
import tech.challenge.orderservice.domain.entities.Pedido
import tech.challenge.orderservice.domain.entities.Produto
import tech.challenge.orderservice.domain.enums.Categoria
import tech.challenge.orderservice.domain.enums.StatusPagamento
import tech.challenge.orderservice.domain.enums.StatusPedido
import java.time.LocalDateTime
import java.util.*

class ItemHelper {

    companion object {
        fun gerarItem(): Item {
            return Item(
                id = UUID.randomUUID(),
                produto = ProdutoHelper.gerarProduto(),
                pedido = PedidoHelper.gerarPedidoComItensVazio(),
                observacoes = "Sem salada!",
                quantidade = 1
            )
        }

        fun gerarItem(pedido: Pedido): Item {
            return Item(
                id = UUID.randomUUID(),
                produto = ProdutoHelper.gerarProduto(),
                pedido = pedido,
                observacoes = "Sem salada!",
                quantidade = 1
            )
        }

        fun gerarListItens(pedido: Pedido): MutableList<Item> {
            return mutableListOf(gerarItem(pedido))
        }

        fun gerarListItens(): MutableList<Item> {
            return mutableListOf(gerarItem())
        }

        fun itemPadrao(): Item{
            return Item(
                id = UUID.randomUUID(),
                produto = Produto(
                    nome = "Classic Burger",
                    preco = 10.00.toBigDecimal(),
                    imagem = "test",
                    descricao = "Pão, hamburguer, queijo, alface",
                    categoria = Categoria.LANCHE
                ),
                pedido = Pedido(
                    idCliente = UUID.randomUUID(),
                    statusPedido = StatusPedido.CRIADO,
                    preco = 10.00.toBigDecimal(),
                    statusPagamento = StatusPagamento.AGUARDANDO_PAGAMENTO,
                    itens = gerarListItens(),
                    dataRecebimento = LocalDateTime.now()
                ),
                observacoes = "Sem salada!",
                quantidade = 1
            )
        }

        fun listaItemPadrao(): MutableList<Item> {
            return mutableListOf(itemPadrao(), itemPadrao())
        }
    }
}