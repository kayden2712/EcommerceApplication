package org.example.ecommerceapplication.service.user;

import org.example.ecommerceapplication.Mapper.UserMapper;
import org.example.ecommerceapplication.dto.Response.user.UserResponse;
import org.example.ecommerceapplication.entity.User;
import org.example.ecommerceapplication.enums.ErrorCode;
import org.example.ecommerceapplication.exception.domain.DuplicateResourceException;
import org.example.ecommerceapplication.exception.domain.ResourceNotFoundException;
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

    public User getUserEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    void validateEmail(String email) {
        if (repository.existsByEmail(email)) {
            throw new DuplicateResourceException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
    }

    @Override
    public User findByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND));
    }
}
