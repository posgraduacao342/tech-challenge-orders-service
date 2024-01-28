package tech.challenge.orderservice.application.exceptions

import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import tech.challenge.orderservice.domain.exception.AtributoInvalidoException
import tech.challenge.orderservice.domain.exception.RecursoJaExisteException
import tech.challenge.orderservice.domain.exception.RecursoNaoEncontradoException

class GlobalDefaultExceptionHandlerTest {

    @Test
    fun `handleResourceBadRequest should return Conflict response for RecursoJaExisteException`() {
        val ex = RecursoJaExisteException("Recurso já existe")
        val handler = GlobalDefaultExceptionHandler()

        val result = handler.handleResourceBadRequest(ex)

        assert(result.statusCode == HttpStatus.CONFLICT)
        assert(result.body?.status == HttpStatus.CONFLICT.value())
        assert(result.body?.title == HttpStatus.CONFLICT.reasonPhrase)
        assert(result.body?.detail == "Recurso já existe")
    }

    @Test
    fun `handleResourceBadRequest should return Not Found response for RecursoNaoEncontradoException`() {
        val ex = RecursoNaoEncontradoException("Recurso não encontrado")
        val handler = GlobalDefaultExceptionHandler()

        val result = handler.handleResourceBadRequest(ex)

        assert(result.statusCode == HttpStatus.NOT_FOUND)
        assert(result.body?.status == HttpStatus.NOT_FOUND.value())
        assert(result.body?.title == HttpStatus.NOT_FOUND.reasonPhrase)
        assert(result.body?.detail == "Recurso não encontrado")
    }

    @Test
    fun `handleResourceBadRequest should return Bad Request response for AtributoInvalidoException`() {
        val ex = AtributoInvalidoException("Atributo inválido")
        val handler = GlobalDefaultExceptionHandler()

        val result = handler.handleResourceBadRequest(ex)

        assert(result.statusCode == HttpStatus.BAD_REQUEST)
        assert(result.body?.status == HttpStatus.BAD_REQUEST.value())
        assert(result.body?.title == HttpStatus.BAD_REQUEST.reasonPhrase)
        assert(result.body?.detail == "Atributo inválido")
    }
}