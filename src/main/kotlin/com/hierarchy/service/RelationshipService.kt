package com.hierarchy.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.hierarchy.entity.RelationshipEntity
import com.hierarchy.exception.HierarchyLoopDetected
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
                persistHierarchy(employeeToSupervisor)
                return mapToResponse(rootSupervisor.single(), employeeToSupervisor)
            }
            else -> throw TooManyRootSupervisors("Looking for 1 root, but found ${rootSupervisor.size}: $rootSupervisor")
        }
    }

    private fun mapToResponse(
        rootSupervisor: String,
        employeeToSupervisor: Map<String, String>
    ): ObjectNode {
        val result = objectMapper.createObjectNode()
        val queue = LinkedList<Pair<ObjectNode, String>>()

        val adjacencyMap: Map<String, List<String>> = employeeToSupervisor.entries.groupBy({it.value}, {it.key})

        queue.add(result to rootSupervisor)

        while(queue.isNotEmpty()) {
            val (node, supervisor) = queue.pop()

            val nextNode = node.putObject(supervisor)

            adjacencyMap[supervisor]?.forEach {
                queue.add(nextNode to it)
            }

        }

        return result
    }

    private fun persistHierarchy(
        employeeToSupervisor: Map<String, String>
    ) {
        val employeeToEntity = mutableMapOf<String, RelationshipEntity>()
        employeeToSupervisor.forEach { (employee, supervisor) ->
            val supervisorEntity = employeeToEntity.getOrDefault(supervisor, RelationshipEntity().apply { name = supervisor })
            val employeeEntity = employeeToEntity.getOrDefault(
                employee,
                RelationshipEntity().apply {
                    this.name = employee
                    this.supervisor = supervisorEntity
                }
            )

            employeeToEntity[employee] = employeeEntity
            employeeToEntity[supervisor] = supervisorEntity
        }

        relationshipRepository.deleteAll()
        relationshipRepository.saveAll(employeeToEntity.values)
    }
}
