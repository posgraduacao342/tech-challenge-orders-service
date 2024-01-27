package tech.challenge.orderservice.infrastructure.db.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

@MappedSuperclass
open class BaseEntity : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = null

    @CreatedDate
    @Column(nullable = false)
    var dataCriacao: LocalDateTime? = null

    @LastModifiedDate
    @Column(nullable = false)
    var dataAtualizacao: LocalDateTime? = null

    @Column
    var dataDelecao: LocalDateTime? = null
}
