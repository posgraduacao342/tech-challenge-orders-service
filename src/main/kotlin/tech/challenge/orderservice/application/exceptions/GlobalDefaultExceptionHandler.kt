package tech.challenge.orderservice.application.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import tech.challenge.orderservice.domain.exception.AtributoInvalidoException
import tech.challenge.orderservice.domain.exception.RecursoJaExisteException
import tech.challenge.orderservice.domain.exception.RecursoNaoEncontradoException

@RestControllerAdvice
class GlobalDefaultExceptionHandler {

    @ExceptionHandler(RecursoJaExisteException::class)
    fun handleResourceBadRequest(ex: RecursoJaExisteException): ResponseEntity<ProblemDetail> {

        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.message ?: "")
        problemDetail.title = HttpStatus.CONFLICT.reasonPhrase

        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail)
    }

    @ExceptionHandler(RecursoNaoEncontradoException::class)
    fun handleResourceBadRequest(ex: RecursoNaoEncontradoException): ResponseEntity<ProblemDetail> {

        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.message ?: "")
        problemDetail.title = (HttpStatus.NOT_FOUND.reasonPhrase)

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail)
    }

    @ExceptionHandler(AtributoInvalidoException::class)
    fun handleResourceBadRequest(ex: AtributoInvalidoException): ResponseEntity<ProblemDetail> {

        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.message ?: "")
        problemDetail.title = (HttpStatus.BAD_REQUEST.reasonPhrase)

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail)
    }
}