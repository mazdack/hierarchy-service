package com.hierarchy.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.hierarchy.dxo.SupervisorsDxo
import com.hierarchy.entity.RelationshipEntity
import com.hierarchy.exception.HierarchyLoopDetected
import com.hierarchy.exception.RelationShipNotFound
import com.hierarchy.exception.TooManyRootSupervisors
import com.hierarchy.repository.RelationshipRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.LinkedList

@Service
class RelationshipService(
    private val relationshipRepository: RelationshipRepository,
    private val objectMapper: ObjectMapper
    ) {
    @Transactional
    fun overwriteRelationships(employeeToSupervisor: Map<String, String>): ObjectNode {
        val employees = employeeToSupervisor.keys
        val supervisors = employeeToSupervisor.values.toSet()

        val rootSupervisor = supervisors - employees

        when(rootSupervisor.size) {
            0 -> throw HierarchyLoopDetected()
            1 -> {
                return persistHierarchy(rootSupervisor.single(), employeeToSupervisor)
            }
            else -> throw TooManyRootSupervisors("Looking for 1 root, but found ${rootSupervisor.size}: $rootSupervisor")
        }
    }

    private fun persistHierarchy(
        rootSupervisor: String,
        employeeToSupervisor: Map<String, String>
    ): ObjectNode {
        val result = objectMapper.createObjectNode()
        val queue = LinkedList<Pair<ObjectNode, String>>()
        val adjacencyMap: Map<String, List<String>> = employeeToSupervisor.entries.groupBy({it.value}, {it.key})
        var previousSupervisor: RelationshipEntity? = null

        queue.add(result to rootSupervisor)
        relationshipRepository.deleteAll()

        while(queue.isNotEmpty()) {
            val (node, supervisor) = queue.pop()

            previousSupervisor = relationshipRepository.save(RelationshipEntity().apply {
                this.name = supervisor
                this.supervisor = previousSupervisor
            })

            val nextNode = node.putObject(supervisor)

            adjacencyMap[supervisor]?.forEach {
                queue.add(nextNode to it)
            }

        }

        return result
    }

    @Transactional
    fun getSupervisors(employeeName: String): SupervisorsDxo = relationshipRepository
        .findByName(employeeName)
        .map {
            SupervisorsDxo(
                it.supervisor?.name,
                it.supervisor?.supervisor?.name
            )
        }
        .orElseThrow { RelationShipNotFound("relationship for name = $employeeName was not found") }
}
