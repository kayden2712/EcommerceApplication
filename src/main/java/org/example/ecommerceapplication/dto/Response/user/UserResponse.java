package org.example.ecommerceapplication.dto.Response.user;

public record UserResponse(
        Long id,
        String email,
        String username,
        String fullName,
        String role
) {
}