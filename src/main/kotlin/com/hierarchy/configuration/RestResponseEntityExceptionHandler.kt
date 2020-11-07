package com.hierarchy.configuration

import com.hierarchy.dxo.ErrorDxo
import com.hierarchy.exception.HierarchyLoopDetected
import com.hierarchy.exception.RelationShipNotFound
import com.hierarchy.exception.TooManyRootSupervisors
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [HierarchyLoopDetected::class, TooManyRootSupervisors::class])
    protected fun handleBadRequestExceptions(ex: RuntimeException, request: WebRequest): ResponseEntity<Any> {
        return handleExceptionInternal(
            ex,
            ErrorDxo(ex.cause?.message),
            HttpHeaders(),
            HttpStatus.BAD_REQUEST,
            request
        )
    }

    @ExceptionHandler(value = [RelationShipNotFound::class])
    protected fun handleNotFoundExceptions(ex: RuntimeException, request: WebRequest): ResponseEntity<Any> {
        return handleExceptionInternal(
            ex,
            ErrorDxo(ex.cause?.message),
            HttpHeaders(),
            HttpStatus.NOT_FOUND,
            request
        )
    }
}
