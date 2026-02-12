package org.example.ecommerceapplication.service.user;

import org.example.ecommerceapplication.dto.Response.user.UserResponse;
import org.example.ecommerceapplication.entity.User;

public interface UserService {
    UserResponse getById(Long id);

    void changePassword(Long id, String newPassword);

    void changeEmail(Long id, String email);

    User findByUsername(String username);
    // void deactivate(Long id);
}
