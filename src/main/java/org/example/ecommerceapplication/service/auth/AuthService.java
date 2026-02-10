package org.example.ecommerceapplication.service.auth;

import org.example.ecommerceapplication.dto.Request.auth.LoginRequest;
import org.example.ecommerceapplication.dto.Request.auth.RegisterRequest;
import org.example.ecommerceapplication.dto.Response.auth.AuthResponse;

public interface AuthService {
    void register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    AuthResponse refresh(String refreshToken);

    void logout(String refreshToken);
}
