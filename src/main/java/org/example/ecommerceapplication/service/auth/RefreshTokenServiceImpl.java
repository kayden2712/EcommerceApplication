package org.example.ecommerceapplication.service.auth;

import java.time.LocalDateTime;
import java.util.UUID;

import org.example.ecommerceapplication.entity.RefreshToken;
import org.example.ecommerceapplication.entity.User;
import org.example.ecommerceapplication.enums.ErrorCode;
import org.example.ecommerceapplication.exception.domain.InvalidOperationException;
import org.example.ecommerceapplication.exception.security.UnauthorizedException;
import org.example.ecommerceapplication.repository.RefreshTokenReponsitory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private static final long REFRESH_TOKEN_DAY = 7;
    private final RefreshTokenReponsitory refreshTokenReponsitory;

    @Override
    @Transactional
    public RefreshToken create(User user) {
        // Xóa refresh token cũ của user (nếu có)
        refreshTokenReponsitory.deleteByUser(user);
        
        RefreshToken token = new RefreshToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(LocalDateTime.now().plusDays(REFRESH_TOKEN_DAY));
        return refreshTokenReponsitory.save(token);
    }

    @Override
    public RefreshToken verify(String token) {
        RefreshToken refreshToken = refreshTokenReponsitory.findByToken(token)
                .orElseThrow(() -> new UnauthorizedException(ErrorCode.INVALID_TOKEN));

        if (refreshToken.isRevoked()) {
            throw new InvalidOperationException(ErrorCode.REFRESH_TOKEN_REVOKED);
        }

        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new InvalidOperationException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }
        return refreshToken;
    }

    @Override
    public void revoke(User user) {
        refreshTokenReponsitory.deleteByUser(user);
    }

}
