package com.example.application.backend

import com.vaadin.flow.server.*
import java.io.IOException
import java.util.*
import javax.servlet.ServletException

//@WebServlet(urlPatterns = "/*", asyncSupported = true)
class AuthServlet : VaadinServlet() {

    @Throws(ServletException::class)
    override fun servletInitialized() {
        super.servletInitialized()
        //        getService().addSessionInitListener(e -> e.getSession().addRequestHandler(new AuthRequestHandler()));
    }

    protected inner class AuthRequestHandler : SynchronizedRequestHandler() {

        @Throws(IOException::class)
        override fun synchronizedHandleRequest(session: VaadinSession, request: VaadinRequest, response: VaadinResponse): Boolean {
            if (Arrays.asList("/form", "/", "/masterdetail").contains((request as VaadinServletRequest).requestURI)) {
                val user = session.getAttribute("user") as String
                val password = session.getAttribute("password") as String

                if (!AuthService.INSTANCE.authenticate(user, password)) {
                    (response as VaadinServletResponse).httpServletResponse.sendRedirect("/login")
                    return true
                }
            }

            return false
        }
    }
}
