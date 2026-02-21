package org.example.ecommerceapplication.auth.service;

import org.example.ecommerceapplication.auth.entity.RefreshToken;
import org.example.ecommerceapplication.user.entity.User;

public interface RefreshTokenService {

    RefreshToken create(User user);

    RefreshToken verify(String token);

    void revoke(User user);
}
