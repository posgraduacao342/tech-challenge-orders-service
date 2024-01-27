package tech.challenge.orderservice.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class RecursoNaoEncontradoException(message: String) : RuntimeException(message)