package org.example.ecommerceapplication.auth.controller;

import org.example.ecommerceapplication.auth.dto.AuthResponse;
import org.example.ecommerceapplication.auth.dto.LoginRequest;
import org.example.ecommerceapplication.auth.dto.RegisterRequest;
import org.example.ecommerceapplication.auth.service.AuthService;
import org.example.ecommerceapplication.common.security.CookieUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final CookieUtil cookieUtil;

    public AuthController(AuthService authService, CookieUtil cookieUtil) {
        this.authService = authService;
        this.cookieUtil = cookieUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        AuthResponse authResponse = authService.login(request);
        
        // Set tokens vào httpOnly cookies
        cookieUtil.createAccessTokenCookie(response, authResponse.accessToken());
        cookieUtil.createRefreshTokenCookie(response, authResponse.refreshToken());
        
        return ResponseEntity.ok("Login successful");
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse response) {
        // Đọc refresh token từ cookie
        String refreshToken = cookieUtil.getRefreshToken(request)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));
        
        AuthResponse authResponse = authService.refresh(refreshToken);
        
        // Set access token mới vào cookie (refresh token giữ nguyên)
        cookieUtil.createAccessTokenCookie(response, authResponse.accessToken());
        
        return ResponseEntity.ok("Token refreshed successfully");
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        // Đọc refresh token từ cookie để revoke trong DB
        // Bắt exception nếu token không hợp lệ, vẫn tiếp tục xóa cookies
        cookieUtil.getRefreshToken(request).ifPresent(token -> {
            try {
                authService.logout(token);
            } catch (Exception e) {
                // Token đã revoked/expired, bỏ qua
            }
        });
        
        // Xóa cả hai cookies (luôn luôn thực hiện)
        cookieUtil.deleteAuthCookies(response);
        
        return ResponseEntity.noContent().build();
    }
}
