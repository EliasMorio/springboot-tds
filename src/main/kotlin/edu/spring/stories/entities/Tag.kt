package edu.spring.stories.entities

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany

@Entity
class Tag {

    @Id
    var id: Int? = null
    var label: String? = null
    var color: String? = null
    @ManyToMany(mappedBy = "tags")
    var stories: MutableSet<Story> = mutableSetOf()
}