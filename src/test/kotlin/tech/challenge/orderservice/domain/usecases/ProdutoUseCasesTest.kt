package tech.challenge.orderservice.domain.usecases

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import tech.challenge.orderservice.application.presenters.mappers.ProdutoMapper
import tech.challenge.orderservice.domain.exception.RecursoNaoEncontradoException
import tech.challenge.orderservice.domain.ports.out.ProdutoGatewayPort
import tech.challenge.orderservice.helpers.AtualizarProdutoHelper
import tech.challenge.orderservice.helpers.ProdutoHelper
import java.util.*

class ProdutoUseCasesTest {

    @Mock
    private lateinit var produtoGatewayPort: ProdutoGatewayPort

    @Mock
    private lateinit var produtoMapper: ProdutoMapper

    @InjectMocks
    private lateinit var produtoUseCases: ProdutoUseCases

    var openMocks: AutoCloseable? = null

    @BeforeEach
    fun setUp() {
        openMocks = MockitoAnnotations.openMocks(this)
    }

    @Test
    fun buscarProdutos_DeveRetornarListaDeProdutos() {
        val produtos = ProdutoHelper.gerarListProdutos()

        `when`(produtoGatewayPort.buscarProdutos()).thenReturn(produtos)

        val result = produtoUseCases.buscarProdutos()

        Assertions.assertEquals(result, produtos)
    }

    @Test
    fun buscarProdutoPorId_DeveRetornarProdutoEncontrado() {
        val produtoId = UUID.randomUUID()
        val produto = ProdutoHelper.gerarProduto()
        produto.id = produtoId

        `when`(produtoGatewayPort.buscarProdutoPorId(produto.id!!)).thenReturn(Optional.of(produto))

        val result = produtoUseCases.buscarProdutoPorId(produto.id!!)

        Assertions.assertEquals(result, produto)
    }

    @Test
    fun buscarProdutoPorId_DeveRetornarErroRecursoNaoEncontradoException() {
        val produtoId = UUID.randomUUID()

        `when`(produtoGatewayPort.buscarProdutoPorId(produtoId)).thenReturn(Optional.empty())

        assertThrows<RecursoNaoEncontradoException> {
            produtoUseCases.buscarProdutoPorId(produtoId)
        }
    }

    @Test
    fun atualizarProduto_DeveRetornarProdutoAtualizado() {
        val request = AtualizarProdutoHelper.gerarAtualizarProduto()

        val produtoId = UUID.randomUUID()
        val produtoExistente = ProdutoHelper.gerarProduto()
        produtoExistente.id = produtoId

        val produtoAtualizado = ProdutoHelper.gerarProduto()
        produtoAtualizado.id = produtoExistente.id
        produtoAtualizado.descricao = request.descricao

        `when`(produtoGatewayPort.buscarProdutoPorId(produtoExistente.id!!)).thenReturn(Optional.of(produtoExistente))
        `when`(produtoGatewayPort.salvarProduto(produtoExistente)).thenReturn(produtoAtualizado)

        val result = produtoUseCases.atualizarProduto(produtoExistente.id!!, request)

        verify(produtoMapper).toDomain(request, produtoExistente)
        verify(produtoGatewayPort).salvarProduto(produtoExistente)
        Assertions.assertEquals(result, produtoAtualizado)
    }

    @Test
    fun deletarProduto_DeveDeletarOProduto() {
        val produtoId = UUID.randomUUID()
        val produto = ProdutoHelper.gerarProduto()
        produto.id = produtoId

        produtoUseCases.deletarProduto(produto.id!!)

        verify(produtoGatewayPort).deletarProduto(produto.id!!)
    }

    @Test
    fun criarNovoProduto_DeveCrairNovoProduto() {
        val produtoId = UUID.randomUUID()
        val produto = ProdutoHelper.gerarProduto()
        produto.id = produtoId

        `when`(produtoGatewayPort.salvarProduto(produto)).thenReturn(produto)
        val result = produtoUseCases.criarNovoProduto(produto)

        verify(produtoGatewayPort).salvarProduto(produto)

        Assertions.assertEquals(result, produto)
    }
}