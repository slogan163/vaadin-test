package com.example.application.backend

import java.util.*

class Employee {

    var id: UUID
    var firstname: String
    var lastname: String
    var title: String? = null
    var email: String? = null
    var notes: String? = null

    constructor(id: UUID, firstname: String, lastname: String, email: String,
                title: String) : this(firstname, lastname, email, title) {
        this.id = id
    }

    constructor(firstname: String, lastname: String, email: String,
                title: String) : super() {
        this.id = UUID.randomUUID()
        this.firstname = firstname
        this.lastname = lastname
        this.email = email
        this.title = title
    }

    override fun toString(): String {
        return "$firstname $lastname($email)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false
        other as Employee

        return this.id == other.id
    }

    override fun hashCode(): Int {
        return this.id.hashCode()
    }
}
