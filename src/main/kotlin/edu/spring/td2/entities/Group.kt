package edu.spring.td2.entities

import jakarta.persistence.*

@Entity
@Table(name = "TD2_GROUP")
open class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id: Int? = null
    open var name: String? = null
    open var email: String? = null
    open var aliases: String? = null

    @ManyToOne
    open var organization: Organization? = null

    @ManyToMany(mappedBy = "groups")
    open var users: MutableSet<User> = mutableSetOf()


    fun addUser(user: User) {
        if (users.contains(user)) return
        users.add(user)
        user.groups.add(this)
    }

    fun removeUser(user: User) {
        if (!users.contains(user)) return
        users.remove(user)
        user.groups.remove(this)
    }
}

