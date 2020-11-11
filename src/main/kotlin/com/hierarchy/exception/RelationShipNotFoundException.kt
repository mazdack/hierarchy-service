package com.hierarchy.exception

import org.springframework.http.HttpStatus

class RelationShipNotFoundException(message: String): RestException(message, HttpStatus.NOT_FOUND)
