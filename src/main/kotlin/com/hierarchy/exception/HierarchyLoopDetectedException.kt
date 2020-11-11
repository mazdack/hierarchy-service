package com.hierarchy.exception

import org.springframework.http.HttpStatus

class HierarchyLoopDetectedException: RestException("hierarchy loop detected", HttpStatus.BAD_REQUEST)
