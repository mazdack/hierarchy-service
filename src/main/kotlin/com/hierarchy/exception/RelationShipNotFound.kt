package com.hierarchy.exception

import org.springframework.http.HttpStatus

class RelationShipNotFound(message: String): RestException(message, HttpStatus.NOT_FOUND)
