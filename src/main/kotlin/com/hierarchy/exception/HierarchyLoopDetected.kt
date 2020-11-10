package com.hierarchy.exception

import org.springframework.http.HttpStatus

class HierarchyLoopDetected: RestException("hierarchy loop detected", HttpStatus.BAD_REQUEST)
