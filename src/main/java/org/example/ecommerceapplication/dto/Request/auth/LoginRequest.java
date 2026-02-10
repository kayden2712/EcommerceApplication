package org.example.ecommerceapplication.dto.Request.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank String usernameOrEmail, @NotBlank String password) {
}
