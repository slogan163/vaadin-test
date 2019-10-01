package com.example.application.backend;

import com.vaadin.flow.server.*;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Arrays;

//@WebServlet(urlPatterns = "/*", asyncSupported = true)
public class AuthServlet extends VaadinServlet {

    @Override
    protected void servletInitialized() throws ServletException {
        super.servletInitialized();
//        getService().addSessionInitListener(e -> e.getSession().addRequestHandler(new AuthRequestHandler()));
    }

    protected class AuthRequestHandler extends SynchronizedRequestHandler {

        @Override
        public boolean synchronizedHandleRequest(VaadinSession session, VaadinRequest request, VaadinResponse response) throws IOException {
            if (Arrays.asList("/form", "/", "/masterdetail").contains(((VaadinServletRequest) request).getRequestURI())) {
                String user = (String) session.getAttribute("user");
                String password = (String) session.getAttribute("password");

                if (!AuthService.INSTANCE.authenticate(user, password)) {
                    ((VaadinServletResponse) response).getHttpServletResponse().sendRedirect("/login");
                    return true;
                }
            }

            return false;
        }
    }
}
