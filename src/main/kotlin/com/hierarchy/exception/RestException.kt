package com.hierarchy.exception

import org.springframework.http.HttpStatus

open class RestException(
    override val message: String,
    val statusCode: HttpStatus
): Throwable()
