package org.example.ecommerceapplication.service.auth;

import org.example.ecommerceapplication.entity.RefreshToken;
import org.example.ecommerceapplication.entity.User;

public interface RefreshTokenService {

    RefreshToken create(User user);

    RefreshToken verify(String token);

    void revoke(User user);
}
