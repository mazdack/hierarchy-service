package com.hierarchy.repository

import com.hierarchy.entity.RelationshipEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RelationshipRepository: CrudRepository<RelationshipEntity, Int>
