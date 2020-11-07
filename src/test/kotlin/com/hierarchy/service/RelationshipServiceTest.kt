package com.hierarchy.service

import com.hierarchy.BaseTest
import com.hierarchy.repository.RelationshipRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class RelationshipServiceTest: BaseTest() {
    @Autowired
    lateinit var relationshipService: RelationshipService
    @Autowired
    lateinit var relationshipRepository: RelationshipRepository

    @BeforeEach
    fun setup() {
        relationshipRepository.deleteAll()
    }

    @Test
    fun `service correctly stores data`() {
        val employeeToSupervisor = mapOf(
            "Tom" to "Jerry",
            "Jerry" to "Thomas",
        )
        relationshipService.overwriteRelationships(employeeToSupervisor)

        val allEmployees = relationshipRepository.findAll().toList()
        assertThat(allEmployees).hasSize(3)

        val tom = allEmployees.find { it.name == "Tom" }
        assertThat(tom).isNotNull
        assertThat(tom!!.supervisor).isNotNull
        assertThat(tom.supervisor!!.name).isEqualTo("Jerry")

        val jerry = allEmployees.find { it.name == "Jerry" }
        assertThat(jerry).isNotNull
        assertThat(jerry!!.supervisor).isNotNull
        assertThat(jerry.supervisor!!.name).isEqualTo("Thomas")
    }

    @Nested
    inner class GetSupervisorsTests {
        @Test
        fun `get supervisors return correct data`() {
            val employeeToSupervisor = mapOf(
                "Tom" to "Jerry",
                "Jerry" to "Thomas",
            )
            relationshipService.overwriteRelationships(employeeToSupervisor)

            val (supervisorName, supervisorSupervisorName) = relationshipService.getSupervisors("Tom")

            assertThat(supervisorName).isEqualTo("Jerry")
            assertThat(supervisorSupervisorName).isEqualTo("Thomas")
        }

        @Test
        fun `get supervisors return correct data when only one supervisor is present`() {
            val employeeToSupervisor = mapOf(
                "Tom" to "Jerry",
            )
            relationshipService.overwriteRelationships(employeeToSupervisor)

            val (supervisorName, supervisorSupervisorName) = relationshipService.getSupervisors("Tom")

            assertThat(supervisorName).isEqualTo("Jerry")
            assertThat(supervisorSupervisorName).isNull()
        }
    }
}
