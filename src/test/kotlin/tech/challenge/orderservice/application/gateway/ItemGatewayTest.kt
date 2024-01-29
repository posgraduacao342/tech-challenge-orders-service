package tech.challenge.orderservice.application.gateway

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.dao.EmptyResultDataAccessException
import tech.challenge.orderservice.application.presenters.mappers.ItemMapper
import tech.challenge.orderservice.domain.entities.Item
import tech.challenge.orderservice.helpers.ItemEntityHelper
import tech.challenge.orderservice.helpers.ItemHelper
import tech.challenge.orderservice.infrastructure.db.entity.ItemEntity
import tech.challenge.orderservice.infrastructure.db.repositories.ItemRepository
import java.util.*

class ItemGatewayTest {

    @Mock
    private lateinit var itemMapper: ItemMapper

    @Mock
    private lateinit var itemRepository: ItemRepository

    @InjectMocks
    private lateinit var itemGateway: ItemGateway

    var openMocks: AutoCloseable? = null

    @BeforeEach
    fun setUp() {
        openMocks = MockitoAnnotations.openMocks(this)
    }

    @Test
    fun atualizarObervacoes_DeveRetornarItem() {
        val item = ItemHelper.gerarItem()
        val itemEntity = ItemEntityHelper.gerarItem()

        `when`(itemMapper.toEntity(item)).thenReturn(itemEntity)
        `when`(itemRepository.save(itemEntity)).thenReturn(itemEntity)
        `when`(itemMapper.toDomain(itemEntity)).thenReturn(item)

        val savedItem = itemGateway.salvarItem(item)

        Assertions.assertNotNull(savedItem)
        Assertions.assertEquals(item, savedItem)
        verify(itemRepository, times(1)).save(itemEntity)
    }

    @Test
    fun atualizarObervacoes_DeveRetornarItemAtualizado() {
        val itemId = UUID.randomUUID()
        val observacao = "Sem carne"
        val existingItemEntity = ItemEntityHelper.gerarItem()
        existingItemEntity.observacoes = observacao
        existingItemEntity.id = itemId

        `when`(itemRepository.findById(itemId)).thenReturn(Optional.of(existingItemEntity))
        `when`(itemRepository.save(any(ItemEntity::class.java))).thenAnswer { invocation ->
            val savedEntity = invocation.arguments[0] as ItemEntity
            savedEntity // Return the saved entity
        }
        `when`(itemMapper.toDomain(any(ItemEntity::class.java))).thenAnswer { invocation ->
            val savedEntity = invocation.arguments[0] as ItemEntity
            Item(id = savedEntity.id, observacoes = savedEntity.observacoes)
        }

        val updatedItem = itemGateway.atualizarObervacoes(itemId, observacao)

        Assertions.assertEquals(observacao, updatedItem.observacoes)
        verify(itemRepository, times(1)).findById(itemId)
        verify(itemRepository, times(1)).save(any(ItemEntity::class.java))
        verify(itemMapper, times(1)).toDomain(any(ItemEntity::class.java))
    }

    @Test
    fun atualizarObervacoes_DeveRetornarExcecao() {
        val itemId = UUID.randomUUID()
        `when`(itemRepository.findById(itemId)).thenReturn(Optional.empty())

        assertThrows<RuntimeException> {
            itemGateway.atualizarObervacoes(itemId, "Novas Observacoes")
        }

        verify(itemRepository, times(1)).findById(itemId)
        verify(itemRepository, never()).save(any(ItemEntity::class.java))
        verify(itemMapper, never()).toDomain(any(ItemEntity::class.java))
    }

    @Test
    fun deletarItem_DeveDeletarItem() {
        val itemId = UUID.randomUUID()

        itemGateway.deletarItem(itemId)

        verify(itemRepository, times(1)).deleteById(itemId)
    }

    @Test
    fun deletarItem_DeveRetornarExcecao() {
        val itemId = UUID.randomUUID()
        doThrow(EmptyResultDataAccessException::class.java).`when`(itemRepository).deleteById(itemId)

        assertThrows<EmptyResultDataAccessException> {
            itemGateway.deletarItem(itemId)
        }

        verify(itemRepository, times(1)).deleteById(itemId)
    }

    @Test
    fun buscarItensPorPedido_DeveRotornarUmaListaDeItem() {
        val pedidoId = UUID.randomUUID()
        val itemEntities = ItemEntityHelper.gerarListItens()
        `when`(itemRepository.findByPedidoId(pedidoId)).thenReturn(itemEntities)
        `when`(itemMapper.toDomain(any(ItemEntity::class.java))).thenReturn(ItemHelper.gerarItem())

        val result = itemGateway.buscarItensPorPedido(pedidoId)

        verify(itemRepository, times(1)).findByPedidoId(pedidoId)
        verify(itemMapper, times(itemEntities.size)).toDomain(any(ItemEntity::class.java))
        Assertions.assertNotNull(result)
        Assertions.assertEquals(itemEntities.size, result!!.size)
    }

    @Test
    fun deletarItensPorPedido_DeveDeletarItem() {
        // Given
        val pedidoId = UUID.randomUUID()
        val itemEntities = ItemEntityHelper.gerarListItens()
        `when`(itemRepository.findByPedidoId(pedidoId)).thenReturn(itemEntities)

        // When
        itemGateway.deletarItensPorPedido(pedidoId)

        // Then
        verify(itemRepository, times(1)).findByPedidoId(pedidoId)
        verify(itemRepository, times(1)).deleteAll(itemEntities)
    }

    @Test
    fun buscarItem_DeveRetornarItem() {
        val itemId = UUID.randomUUID()
        val itemEntity = ItemEntityHelper.gerarItem()
        `when`(itemRepository.findById(itemId)).thenReturn(Optional.of(itemEntity))
        `when`(itemMapper.toDomain(itemEntity)).thenReturn(ItemHelper.gerarItem())

        itemGateway.buscarItem(itemId)

        verify(itemRepository, times(1)).findById(itemId)
        verify(itemMapper, times(1)).toDomain(itemEntity)
    }

    @Test
    fun buscarItemPorIds_DeveRetornarListaDeItem() {
        val ids = listOf(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID())
        val itemEntities = ItemEntityHelper.gerarListItens()
        `when`(itemRepository.findByIdIn(ids)).thenReturn(itemEntities)
        `when`(itemMapper.toDomain(itemEntities)).thenReturn(ItemHelper.gerarListItens())

        itemGateway.buscarItemPorIds(ids)

        verify(itemRepository, times(1)).findByIdIn(ids)
        verify(itemMapper, times(1)).toDomain(itemEntities)
    }

    @Test
    fun atualizarOuSalvarListaItem_DeveRetornarListaDeItemAtualizado() {
        val itens = listOf(ItemHelper.gerarItem(), ItemHelper.gerarItem(), ItemHelper.gerarItem())
        val itensEntity = ItemEntityHelper.gerarListItens()
        `when`(itemMapper.toEntity(itens)).thenReturn(itensEntity)
        `when`(itemRepository.saveAll(itensEntity)).thenReturn(itensEntity)
        `when`(itemMapper.toDomain(itensEntity)).thenReturn(itens)

        itemGateway.atualizarOuSalvarListaItem(itens)

        verify(itemMapper, times(1)).toEntity(itens)
        verify(itemRepository, times(1)).saveAll(itensEntity)
        verify(itemMapper, times(1)).toDomain(itensEntity)
    }
}