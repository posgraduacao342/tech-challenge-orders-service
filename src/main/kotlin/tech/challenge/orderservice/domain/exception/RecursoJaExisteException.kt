package tech.challenge.orderservice.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class RecursoJaExisteException(message: String) : RuntimeException(message)