package org.example.ecommerceapplication.auth.repository;

import java.util.Optional;

import org.example.ecommerceapplication.auth.entity.RefreshToken;
import org.example.ecommerceapplication.common.repository.BaseRepository;
import org.example.ecommerceapplication.user.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenReponsitory extends BaseRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    @Modifying
    @Query("DELETE FROM RefreshToken rt WHERE rt.user = :user")
    void deleteByUser(@Param("user") User user);
}
