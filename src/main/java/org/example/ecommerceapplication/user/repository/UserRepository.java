package org.example.ecommerceapplication.user.repository;

import java.util.Optional;

import org.example.ecommerceapplication.common.repository.BaseRepository;
import org.example.ecommerceapplication.user.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
