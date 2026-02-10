package org.example.ecommerceapplication.service.auth;

import org.example.ecommerceapplication.dto.Request.auth.LoginRequest;
import org.example.ecommerceapplication.dto.Request.auth.RefreshTokenRequest;
import org.example.ecommerceapplication.dto.Request.auth.RegisterRequest;
import org.example.ecommerceapplication.dto.Response.auth.AuthResponse;
import org.example.ecommerceapplication.entity.RefreshToken;
import org.example.ecommerceapplication.entity.User;
import org.example.ecommerceapplication.enums.Role;
import org.example.ecommerceapplication.repository.UserRepository;
import org.example.ecommerceapplication.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void register(RegisterRequest request) {
        User user = new User();

        validateNotEmail(request.email());
        validateUsername(request.username());
        if (request.password().length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters");
        }

        user.setUsername(request.username());
        user.setFullName(request.fullName());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.USER);
        repository.save(user);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = getUser(request.usernameOrEmail());

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid username/email or password");
        }

        String accessToken = jwtUtil.generateToken(user);
        String refreshToken = refreshTokenService.create(user).getToken();
        return new AuthResponse(accessToken, refreshToken);
    }

    @Override
    public AuthResponse refresh(String refreshToken) {
        RefreshToken token = refreshTokenService.verify(refreshToken);
        User user = token.getUser();
        String newToken = jwtUtil.generateToken(user);
        return new AuthResponse(newToken, refreshToken);
    }

    @Override
    public void logout(String refreshToken) {
        RefreshToken token = refreshTokenService.verify(refreshToken);
        refreshTokenService.revoke(token.getUser());
    }

    public User getUser(String name) {
        return repository.findByUsername(name)
                .or(() -> repository.findByEmail(name))
                .orElseThrow(() -> new IllegalStateException("User not found"));
    }

    public void validateNotEmail(String email) {
        if (repository.existsByEmail(email)) {
            throw new IllegalStateException("Email already in use");
        }
    }

    public void validateUsername(String username) {
        if (repository.existsByUsername(username)) {
            throw new IllegalStateException("Username already in use");
        }
    }

}
