package com.example.application.views;

import com.example.application.backend.AuthService;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Tag("sa-login-view")
@Route(value = LoginView.ROUTE)
@PageTitle("Login")
public class LoginView extends VerticalLayout {
    public static final String ROUTE = "login";

    public LoginView() {
        super();

        LoginForm form = new LoginForm();
        form.addLoginListener(e -> {
            boolean isAuthenticated = authenticate(e);
            if (isAuthenticated) {
                form.getUI().ifPresent(ui -> ui.navigate(""));
            } else {
                form.setError(true);
            }
        });

        add(form);
    }

    private boolean authenticate(AbstractLogin.LoginEvent e) {
        String username = e.getUsername();
        String password = e.getPassword();

        if (AuthService.INSTANCE.authenticate(username, password)) {
            VaadinSession session = getUI().get().getSession();
            session.setAttribute("user", username);
            session.setAttribute("password", password);
            return true;
        }

        return false;
    }


}
