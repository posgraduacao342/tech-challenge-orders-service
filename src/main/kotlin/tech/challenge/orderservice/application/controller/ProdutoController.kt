package tech.challenge.orderservice.application.controller

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import tech.challenge.orderservice.application.presenters.mappers.ProdutoMapper
import tech.challenge.orderservice.application.presenters.requests.produto.AtualizarProdutoRequest
import tech.challenge.orderservice.application.presenters.requests.produto.CriarProdutoRequest
import tech.challenge.orderservice.domain.entities.Produto
import tech.challenge.orderservice.domain.enums.Categoria
import tech.challenge.orderservice.domain.ports.`in`.ProdutoUseCasesPort
import java.util.*

@RestController
@RequestMapping("/produtos")
class ProdutoController(
    private val produtoUseCasesPort: ProdutoUseCasesPort,
    private val produtoMapper: ProdutoMapper
) {

    @PostMapping
    fun salvarProduto(@RequestBody @Valid request: CriarProdutoRequest): Produto {
        return produtoUseCasesPort.criarNovoProduto(produtoMapper.toDomain(request))
    }

    @GetMapping
    fun buscarProdutos(): List<Produto> {
        return produtoUseCasesPort.buscarProdutos()
    }

    @PatchMapping("/{produtoId}")
    fun atualizarProduto(@PathVariable produtoId: UUID, @RequestBody request: AtualizarProdutoRequest): Produto {
        return produtoUseCasesPort.atualizarProduto(produtoId, request)
    }

    @DeleteMapping("/{produtoId}")
    fun deletarProduto(@PathVariable produtoId: UUID) {
        produtoUseCasesPort.deletarProduto(produtoId)
    }

    @GetMapping("/{produtoId}")
    fun buscarProdutoPorId(@PathVariable produtoId: UUID): Produto {
        return produtoUseCasesPort.buscarProdutoPorId(produtoId)
    }

    @GetMapping("/porCategoria")
    fun buscarProdutosPorCategoria(@RequestParam categoria: Categoria): List<Produto> {
        return produtoUseCasesPort.buscarProdutosPorCategoria(categoria)
    }
}