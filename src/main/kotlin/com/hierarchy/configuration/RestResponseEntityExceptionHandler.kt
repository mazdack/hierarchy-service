package com.hierarchy.configuration

import com.hierarchy.dxo.ErrorDxo
import com.hierarchy.exception.RestException
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [RestException::class])
    protected fun handleRestExceptions(ex: RuntimeException, request: WebRequest): ResponseEntity<Any> {
        val cause = ex.cause as RestException
        return handleExceptionInternal(
            ex,
            ErrorDxo(cause.message),
            HttpHeaders(),
            cause.statusCode,
            request
        )
    }
}
