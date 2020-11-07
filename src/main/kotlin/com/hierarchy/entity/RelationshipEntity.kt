package com.hierarchy.entity

import org.hibernate.annotations.LazyToOne
import org.hibernate.annotations.LazyToOneOption
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "relationship")
class RelationshipEntity {
    @Id
    @Column(name = "relationship_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column
    lateinit var name: String

    @OneToOne
    @LazyToOne(value = LazyToOneOption.FALSE)
    @JoinColumn(name = "supervisor_relationship_id")
    var supervisor: RelationshipEntity? = null
}
