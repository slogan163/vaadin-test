package com.example.application.backend

class AuthService private constructor() {

    fun authenticate(login: String, password: String): Boolean {
        return "admin" == login && "admin" == password
    }

    companion object {
        val INSTANCE = AuthService()
    }
}
