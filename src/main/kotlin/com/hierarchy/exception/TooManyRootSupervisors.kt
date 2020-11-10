package com.hierarchy.exception

import org.springframework.http.HttpStatus

class TooManyRootSupervisors(message: String) : RestException(message, HttpStatus.BAD_REQUEST)
