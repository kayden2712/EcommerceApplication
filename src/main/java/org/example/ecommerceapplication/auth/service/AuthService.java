package org.example.ecommerceapplication.auth.service;

import org.example.ecommerceapplication.auth.dto.LoginRequest;
import org.example.ecommerceapplication.auth.dto.RegisterRequest;
import org.example.ecommerceapplication.auth.dto.AuthResponse;

public interface AuthService {
    void register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    AuthResponse refresh(String refreshToken);

    void logout(String refreshToken);
}
