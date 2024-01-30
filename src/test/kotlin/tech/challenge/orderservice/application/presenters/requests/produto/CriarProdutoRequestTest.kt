package tech.challenge.orderservice.application.presenters.requests.produto

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import tech.challenge.orderservice.domain.enums.Categoria
import java.math.BigDecimal

class CriarProdutoRequestTest {

    @Test
    fun criarProdutoRequestValoresPadrao() {
        val criarProdutoRequest = CriarProdutoRequest()

        Assertions.assertEquals(null, criarProdutoRequest.nome)
        Assertions.assertEquals(null, criarProdutoRequest.preco)
        Assertions.assertEquals(null, criarProdutoRequest.imagem)
        Assertions.assertEquals(null, criarProdutoRequest.descricao)
        Assertions.assertEquals(null, criarProdutoRequest.categoria)
    }

    @Test
    fun criarProdutoRequestAtribuindoValores() {
        val nome = "Produto Teste"
        val preco = BigDecimal.valueOf(20.0)
        val imagem = "imagem.jpg"
        val descricao = "Descrição do produto teste"
        val categoria = Categoria.LANCHE

        val criarProdutoRequest = CriarProdutoRequest(
            nome = nome,
            preco = preco,
            imagem = imagem,
            descricao = descricao,
            categoria = categoria
        )

        Assertions.assertEquals(nome, criarProdutoRequest.nome)
        Assertions.assertEquals(preco, criarProdutoRequest.preco)
        Assertions.assertEquals(imagem, criarProdutoRequest.imagem)
        Assertions.assertEquals(descricao, criarProdutoRequest.descricao)
        Assertions.assertEquals(categoria, criarProdutoRequest.categoria)
    }

    @Test
    fun criarProdutoRequestModificandoValores() {
        val criarProdutoRequest = CriarProdutoRequest()

        val novoProdutoRequest = criarProdutoRequest.copy(
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