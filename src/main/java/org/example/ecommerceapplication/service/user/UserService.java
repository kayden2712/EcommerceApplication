package org.example.ecommerceapplication.service.user;

import org.example.ecommerceapplication.dto.Response.user.UserResponse;

public interface UserService {
    UserResponse getById(Long id);

    void changePassword(Long id, String newPassword);

    void changeEmail(Long id, String email);

    void deactivate(Long id);
}
