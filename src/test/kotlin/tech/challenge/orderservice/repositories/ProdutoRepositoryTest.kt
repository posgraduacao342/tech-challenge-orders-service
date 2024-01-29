package tech.challenge.orderservice.repositories

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import tech.challenge.orderservice.domain.enums.Categoria
import tech.challenge.orderservice.helpers.ProdutoEntityHelper
import tech.challenge.orderservice.infrastructure.db.repositories.ProdutoRepository

class ProdutoRepositoryTest {

    @Mock
    private lateinit var produtoRepository: ProdutoRepository

    var openMocks: AutoCloseable? = null

    @BeforeEach
    fun setUp() {
        openMocks = MockitoAnnotations.openMocks(this)
    }

    @Test
    fun findByCategoria_DeveRetornarListaDeProdutos() {
        // Given
        val categoria = Categoria.LANCHE
        val produtos = ProdutoEntityHelper.gerarListProdutos()

        produtoRepository.saveAll(produtos)

        // Mocking the behavior of produtoRepository.findByCategoria
        Mockito.`when`(produtoRepository.findByCategoria(categoria)).thenReturn(produtos.filter { it.categoria == categoria })

        // When
        val result = produtoRepository.findByCategoria(categoria)

        // Then
        Assertions.assertEquals(1, result.size)
        Assertions.assertTrue { result.all { it.categoria == categoria } }
    }

    @Test
    fun `findByIdIn should return a list of produtos for given ids`() {
        // Given
        val produtos = ProdutoEntityHelper.gerarListProdutos()

        produtoRepository.saveAll(produtos)

        // Mocking the behavior of produtoRepository.findByIdIn
        Mockito.`when`(produtoRepository.findByIdIn(produtos.map { it.id })).thenReturn(produtos)

        // When
        val result = produtoRepository.findByIdIn(produtos.map { it.id })

        // Then
        Assertions.assertEquals(1, result.size)
        Assertions.assertTrue { result.containsAll(produtos) }
    }
}