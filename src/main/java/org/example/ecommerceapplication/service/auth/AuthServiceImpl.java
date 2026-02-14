package org.example.ecommerceapplication.service.auth;

import org.example.ecommerceapplication.dto.Request.auth.LoginRequest;
import org.example.ecommerceapplication.dto.Request.auth.RegisterRequest;
import org.example.ecommerceapplication.dto.Response.auth.AuthResponse;
import org.example.ecommerceapplication.entity.RefreshToken;
import org.example.ecommerceapplication.entity.User;
import org.example.ecommerceapplication.enums.ErrorCode;
import org.example.ecommerceapplication.enums.Role;
import org.example.ecommerceapplication.exception.domain.DuplicateResourceException;
import org.example.ecommerceapplication.exception.domain.InvalidOperationException;
import org.example.ecommerceapplication.exception.domain.ResourceNotFoundException;
import org.example.ecommerceapplication.exception.security.UnauthorizedException;
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
        validateNotEmail(request.email());
        validateUsername(request.username());
        validatePassword(request.password());

        User user = new User();
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
            throw new UnauthorizedException(ErrorCode.INVALID_CREDENTIALS);
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

    private User getUser(String name) {
        return repository.findByUsername(name)
                .or(() -> repository.findByEmail(name))
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    private void validateNotEmail(String email) {
        if (repository.existsByEmail(email)) {
            throw new DuplicateResourceException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
    }

    private void validateUsername(String username) {
        if (repository.existsByUsername(username)) {
            throw new DuplicateResourceException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < 8) {
            throw new InvalidOperationException(ErrorCode.PASSWORD_TOO_SHORT);
        }
    }
}
