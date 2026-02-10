package org.example.ecommerceapplication.service.auth;

import java.time.LocalDateTime;
import java.util.UUID;

import org.example.ecommerceapplication.entity.RefreshToken;
import org.example.ecommerceapplication.entity.User;
import org.example.ecommerceapplication.repository.RefreshTokenReponsitory;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private static final long REFRESH_TOKEN_DAY = 7;
    private final RefreshTokenReponsitory refreshTokenReponsitory;

    @Override
    public RefreshToken create(User user) {
        RefreshToken token = new RefreshToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(LocalDateTime.now().plusDays(REFRESH_TOKEN_DAY));
        return refreshTokenReponsitory.save(token);
    }

    @Override
    public RefreshToken verify(String token) {
        RefreshToken refreshToken = refreshTokenReponsitory.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));

        if (refreshToken.isRevoked()) {
            throw new IllegalStateException("Refresh token has been revoked");
        }

        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Refresh token has expired");
        }
        return refreshToken;
    }

    @Override
    public void revoke(User user) {
        refreshTokenReponsitory.deleteByUser(user);
    }

}
