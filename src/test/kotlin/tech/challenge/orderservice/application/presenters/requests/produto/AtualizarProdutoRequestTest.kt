package tech.challenge.orderservice.application.presenters.requests.produto

import org.junit.jupiter.api.Test
import tech.challenge.orderservice.domain.enums.Categoria
import org.junit.jupiter.api.Assertions
import java.math.BigDecimal

class AtualizarProdutoRequestTest {

    @Test
    fun atualizarProdutoRequestValoresPadrao() {
        val atualizarProdutoRequest = AtualizarProdutoRequest()

        Assertions.assertEquals(null, atualizarProdutoRequest.nome)
        Assertions.assertEquals(null, atualizarProdutoRequest.preco)
        Assertions.assertEquals(null, atualizarProdutoRequest.imagem)
        Assertions.assertEquals(null, atualizarProdutoRequest.descricao)
        Assertions.assertEquals(null, atualizarProdutoRequest.categoria)
    }

    @Test
    fun atualizarProdutoRequestAtribuindoValores() {
        val nome = "Produto Teste"
        val preco = BigDecimal.valueOf(20.0)
        val imagem = "imagem.jpg"
        val descricao = "Descrição do produto teste"
        val categoria = Categoria.LANCHE

        val atualizarProdutoRequest = AtualizarProdutoRequest(
            nome = nome,
            preco = preco,
            imagem = imagem,
            descricao = descricao,
            categoria = categoria
        )

        Assertions.assertEquals(nome, atualizarProdutoRequest.nome)
        Assertions.assertEquals(preco, atualizarProdutoRequest.preco)
        Assertions.assertEquals(imagem, atualizarProdutoRequest.imagem)
        Assertions.assertEquals(descricao, atualizarProdutoRequest.descricao)
        Assertions.assertEquals(categoria, atualizarProdutoRequest.categoria)
    }

    @Test
    fun atualizarProdutoRequestModificandoValores() {
        val atualizarProdutoRequest = AtualizarProdutoRequest()

        val novoProdutoRequest = atualizarProdutoRequest.copy(
            nome = "Novo Produto",
            preco = BigDecimal.valueOf(30.0),
            imagem = "nova-imagem.jpg",
            descricao = "Nova descrição",
            categoria = Categoria.BEBIDA
        )

        Assertions.assertEquals("Novo Produto", novoProdutoRequest.nome)
        Assertions.assertEquals(BigDecimal.valueOf(30.0), novoProdutoRequest.preco)
        Assertions.assertEquals("nova-imagem.jpg", novoProdutoRequest.imagem)
        Assertions.assertEquals("Nova descrição", novoProdutoRequest.descricao)
        Assertions.assertEquals(Categoria.BEBIDA, novoProdutoRequest.categoria)
    }
}