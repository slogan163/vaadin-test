package com.example.application.backend;

import com.vaadin.flow.server.ServiceException;
import com.vaadin.flow.server.SessionInitEvent;
import com.vaadin.flow.server.SessionInitListener;
import com.vaadin.flow.server.VaadinServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet(urlPatterns = "/*", asyncSupported = true)
public class AuthServlet extends VaadinServlet implements SessionInitListener {

    @Override
    protected void servletInitialized() throws ServletException {
        super.servletInitialized();
        getService().addSessionInitListener(this);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (Arrays.asList("/form", "/", "/masterdetail").contains(request.getRequestURI())
                && request.getAuthType() == null) {

            response.sendRedirect("login");
        } else {
            super.service(request, response);
        }
    }

    @Override
    public void sessionInit(SessionInitEvent event) throws ServiceException {
        System.out.println();
        // Do session start stuff here
    }
}
