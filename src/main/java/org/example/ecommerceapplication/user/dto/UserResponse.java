package org.example.ecommerceapplication.user.dto;

public record UserResponse(
        Long id,
        String email,
        String username,
        String fullName,
        String role
) {
}