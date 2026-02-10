package org.example.ecommerceapplication.repository;

import java.util.Optional;

import org.example.ecommerceapplication.entity.RefreshToken;
import org.example.ecommerceapplication.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenReponsitory extends BaseRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(User  user);
}
