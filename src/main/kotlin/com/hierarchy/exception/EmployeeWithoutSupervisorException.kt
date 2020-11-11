package com.hierarchy.exception

import org.springframework.http.HttpStatus

class EmployeeWithoutSupervisorException : RestException(
    "Employee without supervisor found",
    HttpStatus.BAD_REQUEST
)
