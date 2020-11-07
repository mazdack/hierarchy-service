package com.hierarchy.controller

import com.fasterxml.jackson.databind.node.ObjectNode
import com.hierarchy.dxo.SupervisorsDxo
import com.hierarchy.service.RelationshipService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/relationship")
class RelationshipController(private val relationshipService: RelationshipService) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun createRelationships(@RequestBody employeeToSupervisor: Map<String, String>): ObjectNode {
        return relationshipService.overwriteRelationships(employeeToSupervisor)
    }

    @GetMapping("/supervisors")
    fun getSuperVisors(@RequestParam employeeName: String): ResponseEntity<SupervisorsDxo> {
        if (employeeName.isBlank()) {
            return ResponseEntity.badRequest().build()
        }

        return ResponseEntity.ok(relationshipService.getSupervisors(employeeName))
    }

}
