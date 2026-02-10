package org.example.ecommerceapplication.service.auth;

import lombok.RequiredArgsConstructor;

import org.example.ecommerceapplication.dto.Request.auth.LoginRequest;
import org.example.ecommerceapplication.dto.Request.auth.RegisterRequest;
import org.example.ecommerceapplication.dto.Response.auth.AuthResponse;
import org.example.ecommerceapplication.entity.User;
import org.example.ecommerceapplication.repository.UserRepository;
import org.example.ecommerceapplication.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public void register(RegisterRequest request) {
        User user = new User();

        validateNotEmail(request.email());
        validateUsername(request.username());

        user.setUsername(request.username());
        user.setFullName(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        repository.save(user);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = getUser(request.name());
        valideteEmailAndPassword(user.getEmail(), request.password());
        String accessToken = jwtUtil.generateToken(user);
        return new AuthResponse(accessToken);
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

    public void valideteEmailAndPassword(String email, String password) {
        User user = repository.findByEmail(email).orElseThrow(() -> new IllegalStateException("Invalid email or password"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalStateException("Invalid email or password");
        }
    }
}
