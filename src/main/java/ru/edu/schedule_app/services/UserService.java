package ru.edu.schedule_app.services;


import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.edu.schedule_app.entities.auth.RegisterRequest;
import ru.edu.schedule_app.entities.user.User;
import ru.edu.schedule_app.entities.user.UserDto;
import ru.edu.schedule_app.entities.user.UserRole;
import ru.edu.schedule_app.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final ClassService classService;
    private final SubjectService subjectService;

    public User getUser(String email) {
        return repository.getByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with email " + email + "not found"));
    }

    public User getTeacherById(String id) {
        User user = getUserById(id);
        if (user.getRole() != null && user.getRole() == UserRole.TEACHER) {
            return user;
        } else {
            throw new EntityNotFoundException("Teacher with id" + id + "was not found");
        }
    }

    private User getUserById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + "was not found"));
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
                .role(role)
                .build();
        return repository.save(user);
    }

    private UserRole extractRole(String role) {
        return switch (role) {
            case "TEACHER" -> UserRole.TEACHER;
            case "STUDENT" -> UserRole.STUDENT;
            case "ADMIN" -> UserRole.ADMIN;
            default -> throw new EntityNotFoundException("Role " + role + " not found");
        };
    }

    public User getStudentById(String id) {
        User user = getUserById(id);
        if (user.getRole() != null && user.getRole() == UserRole.STUDENT) {
            return user;
        } else {
            throw new EntityNotFoundException("Student with id" + id + "was not found");
        }
    }

    public List<User> getTeachers(List<String> teacherIds) {
        return teacherIds.stream().map(this::getTeacherById).toList();
    }

    public List<User> getStudents(List<String> studentIds) {
        return studentIds.stream().map(this::getStudentById).toList();
    }

    public UserDto convertToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getName(),
                user.getGroup().getId(),
                user.getRole().toString(),
                classService.getClassIds(user.getClassesToTeach()),
                subjectService.getSubjectIds(user.getClassesToTeach())
        );
    }
}