package tech.challenge.orderservice.application.gateway

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import tech.challenge.orderservice.application.presenters.mappers.ProdutoMapper
import tech.challenge.orderservice.domain.entities.Produto
import tech.challenge.orderservice.domain.enums.Categoria
import tech.challenge.orderservice.helpers.ProdutoEntityHelper
import tech.challenge.orderservice.helpers.ProdutoHelper
import tech.challenge.orderservice.infrastructure.db.repositories.ProdutoRepository
import java.math.BigDecimal
import java.util.*

class ProdutoGatewayTest {

    @Mock
    private lateinit var produtoMapper: ProdutoMapper

    @Mock
    private lateinit var produtoRepository: ProdutoRepository

    @InjectMocks
    private lateinit var produtoGateway: ProdutoGateway

    var openMocks: AutoCloseable? = null

    @BeforeEach
    fun setUp() {
        openMocks = MockitoAnnotations.openMocks(this)
    }

    @Test
    fun buscarProdutoPorId_DeveRetornarUmProduto() {
        val produtoId = UUID.randomUUID()
        val produtoEntity = ProdutoEntityHelper.gerarProduto()
        val produto = ProdutoHelper.gerarProduto()

        `when`(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produtoEntity))

        `when`(produtoMapper.toDomain(produtoEntity)).thenReturn(produto)

        val result = produtoGateway.buscarProdutoPorId(produtoId)

        Assertions.assertTrue(result.isPresent)
        Assertions.assertEquals(produto, result.get())
        verify(produtoRepository).findById(produtoId)
        verify(produtoMapper).toDomain(produtoEntity)
    }

    @Test
    fun buscarProdutoPorIds_DeveRetonarListaDeProdutos(){
        // Given
        val produtoIds = listOf(UUID.randomUUID(), UUID.randomUUID())
        val produtoEntities = ProdutoEntityHelper.gerarListProdutos()
        val produtos = ProdutoHelper.gerarListProdutos()

        `when`(produtoRepository.findByIdIn(produtoIds)).thenReturn(produtoEntities)

        `when`(produtoMapper.toDomain(produtoEntities)).thenReturn(produtos)

        val result = produtoGateway.buscarProdutoPorIds(produtoIds)

        Assertions.assertEquals(produtos.size, result.size)
        Assertions.assertEquals(produtos, result)

        verify(produtoRepository).findByIdIn(produtoIds)

        verify(produtoMapper).toDomain(produtoEntities)
    }

    @Test
    fun buscarProdutosPorCategoria_DeveRetonarListaDeProdutos() {
        // Given
        val categoria = Categoria.LANCHE
        val produtoEntities = ProdutoEntityHelper.gerarListProdutos()
        val produtos = ProdutoHelper.gerarListProdutos()
        produtoEntities[0].categoria = categoria

        `when`(produtoRepository.findByCategoria(categoria)).thenReturn(produtoEntities)

        `when`(produtoMapper.toDomainList(produtoEntities)).thenReturn(produtos)

        val result = produtoGateway.buscarProdutosPorCategoria(categoria)

        Assertions.assertEquals(produtos.size, result.size)
        Assertions.assertEquals(produtos, result)
        verify(produtoRepository).findByCategoria(categoria)
        verify(produtoMapper).toDomainList(produtoEntities)
    }

    @Test
    fun deletarProduto_DeveDeletarUmProduto() {
        val produtoId = UUID.randomUUID()

        produtoGateway.deletarProduto(produtoId)

        verify(produtoRepository).deleteById(produtoId)
    }

    @Test
    fun salvarProduto_DeveSalvarERetornarProduto() {
        val produto = Produto(
            nome = "Produto Teste",
            preco = BigDecimal.valueOf(20.0),
            imagem = "imagem.jpg",
            descricao = "Descrição do produto teste",
            categoria = Categoria.LANCHE
        )
        val produtoEntity = ProdutoEntityHelper.gerarProduto(produto)
        val produtoSalvo = ProdutoHelper.gerarProduto(produto)

        `when`(produtoMapper.toEntity(produto)).thenReturn(produtoEntity)
        `when`(produtoRepository.save(any())).thenReturn(produtoEntity)
        `when`(produtoMapper.toDomain(produtoEntity)).thenReturn(produtoSalvo)

        val result = produtoGateway.salvarProduto(produto)

        Assertions.assertEquals(produtoSalvo, result)
        verify(produtoMapper).toEntity(produto)
        verify(produtoRepository).save(any())
        verify(produtoMapper).toDomain(produtoEntity)
    }
}