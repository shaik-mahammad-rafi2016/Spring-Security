package com.security.Jwt.Implementation.Project.service;

import com.security.Jwt.Implementation.Project.dto.RegisterRequest;
import com.security.Jwt.Implementation.Project.entity.Role;
import com.security.Jwt.Implementation.Project.entity.User;
import com.security.Jwt.Implementation.Project.repository.RoleRepository;
import com.security.Jwt.Implementation.Project.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(RegisterRequest request) {

        // 1. Check whether username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        // 2. Encrypt the password
        String encodedPassword =
                passwordEncoder.encode(request.getPassword());

        // 3. Find the default USER role
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() ->
                        new RuntimeException("Default USER role not found"));

        // 4. Create and save the user
        User user = new User();

        user.setUsername(request.getUsername());
        user.setPassword(encodedPassword);
        user.getRoles().add(userRole);

        return userRepository.save(user);
    }
}
