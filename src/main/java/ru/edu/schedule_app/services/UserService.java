package ru.edu.schedule_app.services;


import ru.edu.schedule_app.entities.auth.RegisterRequest;
import ru.edu.schedule_app.entities.user.User;
import ru.edu.schedule_app.entities.user.UserDto;
import ru.edu.schedule_app.entities.user.UserRole;
import ru.edu.schedule_app.repositories.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public User getUser(String email) {
        return repository.getByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with email " + email + "not found"));
    }

    public User setUser(RegisterRequest request) {
        Optional<User> optUser = repository.getByEmail(request.getEmail());
        UserRole role = extractRole(request.getRole());
        if (optUser.isPresent()) {
            throw new EntityExistsException("User with email: " + request.getEmail() + " already exists");
        }
        var user = User.builder()
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .name(request.getName())
                .age(request.getAge())
                .role(role)
                .build();
        return repository.save(user);
    }

    private UserRole extractRole(String role) {
        return switch (role) {
            case "USER" -> UserRole.USER;
            case "ADMIN" -> UserRole.ADMIN;
            default -> throw new EntityNotFoundException("Role " + role + " not found");
        };
    }

    public UserDto getNextPair(String userId) {
        return null;
    }
}