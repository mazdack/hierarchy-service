package com.hierarchy.exception

import org.springframework.http.HttpStatus

class EmployeeHierarchyIsEmptyException: RestException(
    "Employee hierarchy not provided",
    HttpStatus.BAD_REQUEST
)
