package com.example.application.views

import com.example.application.backend.AuthService
import com.vaadin.flow.component.Tag
import com.vaadin.flow.component.login.AbstractLogin
import com.vaadin.flow.component.login.LoginForm
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route

@Tag("sa-login-view")
@Route(value = "login")
@PageTitle("Login")
class LoginView : VerticalLayout() {
    init {

        val form = LoginForm()
        form.addLoginListener { e ->
            val isAuthenticated = authenticate(e)
            if (isAuthenticated) {
                form.ui.ifPresent { ui -> ui.navigate("") }
            } else {
                form.isError = true
            }
        }

        add(form)
    }

    private fun authenticate(e: AbstractLogin.LoginEvent): Boolean {
        val username = e.username
        val password = e.password

        if (AuthService.authenticate(username, password)) {
            val session = ui.get().session
            session.setAttribute("user", username)
            session.setAttribute("password", password)
            return true
        }

        return false
    }
}
