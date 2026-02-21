package org.example.ecommerceapplication.user.service;

import org.example.ecommerceapplication.user.dto.UserResponse;
import org.example.ecommerceapplication.user.entity.User;

public interface UserService {
    UserResponse getById(Long id);

    void changePassword(Long id, String newPassword);

    void changeEmail(Long id, String email);

    User findByUsername(String username);
}
