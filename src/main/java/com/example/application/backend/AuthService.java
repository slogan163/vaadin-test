package com.example.application.backend;

public class AuthService {
    public static final AuthService INSTANCE = new AuthService();

    private AuthService() {
    }

    public boolean authenticate(String login, String password) {
        return "admin".equals(login) && "admin".equals(password);
    }
}
