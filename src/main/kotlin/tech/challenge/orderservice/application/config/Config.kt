package tech.challenge.orderservice.application.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tech.challenge.orderservice.application.gateway.ItemGateway
import tech.challenge.orderservice.application.gateway.PedidoGateway
import tech.challenge.orderservice.application.gateway.ProdutoGateway
import tech.challenge.orderservice.application.presenters.mappers.PedidoQueueMapper
import tech.challenge.orderservice.application.presenters.mappers.ProdutoMapper
import tech.challenge.orderservice.domain.ports.out.PagamentoQueueAdapterOUTPort
import tech.challenge.orderservice.domain.usecases.ItemUseCases
import tech.challenge.orderservice.domain.usecases.PedidoUseCases
import tech.challenge.orderservice.domain.usecases.ProdutoUseCases

@Configuration
class Config {

    @Bean
    fun itemUseCasesConfig(itemGateway: ItemGateway): ItemUseCases {
        return ItemUseCases(itemGateway)
    }

    @Bean
    fun pedidoUseCasesConfig(
        pedidoGateway: PedidoGateway,
        produtoGateway: ProdutoGateway,
        pagamentoQueueAdapterOUTPort: PagamentoQueueAdapterOUTPort,
        pedidoQueueMapper: PedidoQueueMapper,
    ): PedidoUseCases {
        return PedidoUseCases(pedidoGateway, produtoGateway, pagamentoQueueAdapterOUTPort, pedidoQueueMapper)
    }

    @Bean
    fun produtoUseCasesConfig(
        produtoGateway: ProdutoGateway,
        produtoMapper: ProdutoMapper
    ): ProdutoUseCases {
        return ProdutoUseCases(produtoGateway, produtoMapper)
    }
}