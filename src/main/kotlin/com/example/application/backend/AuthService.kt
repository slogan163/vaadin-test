package com.example.application.backend

object AuthService {

    fun authenticate(login: String, password: String): Boolean {
        return "admin" == login && "admin" == password
    }

}
