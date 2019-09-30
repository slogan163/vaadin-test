package com.example.application.backend;

import com.vaadin.flow.server.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

@WebServlet(urlPatterns = "/*", asyncSupported = true)
public class AuthServlet extends VaadinServlet implements SessionInitListener {

    @Override
    protected void servletInitialized() throws ServletException {
        super.servletInitialized();
        getService().addSessionInitListener(this);
    }


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (Arrays.asList("/form", "/", "/masterdetail").contains(request.getRequestURI())) {

            Collection<VaadinSession> sessions = VaadinSession.getAllSessions(request.getSession());
            VaadinSession session = sessions.stream().findFirst().orElse(null);
            if (session != null) {
                String user = (String) session.getAttribute("user");
                String password = (String) session.getAttribute("password");

                if (!AuthService.INSTANCE.authenticate(user, password)) {
                    response.sendRedirect("login");
                } else {
                    super.service(request, response);
                }
            }
        } else {
            super.service(request, response);
        }
    }

    @Override
    public void sessionInit(SessionInitEvent event) throws ServiceException {

    }
}
