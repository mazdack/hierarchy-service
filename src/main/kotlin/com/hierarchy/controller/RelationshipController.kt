package com.hierarchy.controller

import com.hierarchy.service.RelationshipService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/relationship")
class RelationshipController(private val relationshipService: RelationshipService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createRelationships(@RequestBody employeeToSupervisor: Map<String, String>) {
        relationshipService.overwriteRelationships(employeeToSupervisor)
    }

}
