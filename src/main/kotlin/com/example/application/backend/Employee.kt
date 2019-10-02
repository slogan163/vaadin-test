package com.example.application.backend

import java.util.*

class Employee {

    var id: UUID? = null
    var firstname: String? = null
    var lastname: String? = null
    var title: String? = null
    var email: String? = null
    var notes: String? = null

    constructor(id: UUID, firstname: String, lastname: String, email: String,
                title: String) : super() {
        this.id = id
        this.firstname = firstname
        this.lastname = lastname
        this.email = email
        this.title = title
    }

    constructor() {

    }

    override fun toString(): String {
        return "$firstname $lastname($email)"
    }
}
