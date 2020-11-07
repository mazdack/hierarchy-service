package com.hierarchy.service

import com.hierarchy.entity.RelationshipEntity
import com.hierarchy.repository.RelationshipRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RelationshipService(private val relationshipRepository: RelationshipRepository) {
    @Transactional
    fun overwriteRelationships(employeeToSupervisor: Map<String, String>) {
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
