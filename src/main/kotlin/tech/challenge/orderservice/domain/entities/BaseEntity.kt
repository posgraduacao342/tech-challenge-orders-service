package tech.challenge.orderservice.domain.entities

import java.time.LocalDateTime
import java.util.*

open class BaseEntity(
        var id: UUID? = null,
        var dataCriacao: LocalDateTime? = null,
        var dataDelecao: LocalDateTime? = null,
        var dataAtualizacao: LocalDateTime? = null
)
