package org.example.ecommerceapplication.service.user;

import org.example.ecommerceapplication.Mapper.UserMapper;
import org.example.ecommerceapplication.dto.Response.user.UserResponse;
import org.example.ecommerceapplication.entity.User;
import org.example.ecommerceapplication.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserResponse getById(Long id) {
        return userMapper.toResponse(getUserEntity(id));
    }

    @Override
    public void changePassword(Long id, String newPass) {
        User user = getUserEntity(id);
        user.setPassword(encoder.encode(newPass));
        repository.save(user);
    }

    @Override
    public void changeEmail(Long id, String newEmail) {
        User user = getUserEntity(id);
        validateEmail(newEmail);
        user.setEmail(newEmail);
        repository.save(user);
    }

//     @Override
//     public void deactivate(Long id) {
//         User user = getUserEntity(id);
// //        user.setIsDeleted(true);
//     }
    public User getUserEntity(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalStateException("User not found with id: " + id));
    }

    void validateEmail(String email) {
        boolean exists = repository.existsByEmail(email);
        if (exists) {
            throw new RuntimeException("Email is already in use: " + email);
        }
    }

    // void validateUsername(String username) {
    //     boolean exists = repository.existsByUsername(username);
    //     if (exists) {
    //         throw new RuntimeException("Username is already in use: " + username);
    //     }
    // }
    @Override
    public User findByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User not found with username: " + username));
    }
}
