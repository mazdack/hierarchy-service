package com.hierarchy.exception

import org.springframework.http.HttpStatus

class TooManyRootSupervisorsException(message: String) : RestException(message, HttpStatus.BAD_REQUEST)
