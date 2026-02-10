package org.example.ecommerceapplication.service.auth;

import lombok.RequiredArgsConstructor;
import org.example.ecommerceapplication.entity.User;
import org.example.ecommerceapplication.model.CustomerUserDetails;
import org.example.ecommerceapplication.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new CustomerUserDetails(user);
    }
}
