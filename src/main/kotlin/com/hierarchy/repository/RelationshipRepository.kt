package com.hierarchy.repository

import com.hierarchy.entity.RelationshipEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface RelationshipRepository: CrudRepository<RelationshipEntity, Int> {
    fun findByName(name: String): Optional<RelationshipEntity>
}
