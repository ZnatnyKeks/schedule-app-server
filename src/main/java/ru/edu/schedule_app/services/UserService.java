package ru.edu.schedule_app.services;


import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.edu.schedule_app.entities.auth.RegisterRequest;
import ru.edu.schedule_app.entities.school_class.SchoolClass;
import ru.edu.schedule_app.entities.subject.Subject;
import ru.edu.schedule_app.entities.user.User;
import ru.edu.schedule_app.entities.user.UserDto;
import ru.edu.schedule_app.entities.user.UserRole;
import ru.edu.schedule_app.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements EntityService<User, UserDto> {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    private String notFoundMessage(String id) {
        return "User with id " + id + "was not found";
    }
    public User getUser(String email) {
        return repository.getByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(notFoundMessage(email)));
    }

    public User getTeacherById(String id) {
        User user = getById(id);
        if (user.getRole() != null && user.getRole() == UserRole.TEACHER) {
            return user;
        } else {
            throw new EntityNotFoundException(notFoundMessage(id));
        }
    }

    @Override
    public User getById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(notFoundMessage(id)));
    }

    public User setUser(RegisterRequest request) {
        Optional<User> optUser = repository.getByEmail(request.getEmail());
        UserRole role = extractRole(request.getRole());
        if (optUser.isPresent()) {
            throw new EntityExistsException(notFoundMessage(request.getEmail()));
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
        User user = getById(id);
        if (user.getRole() != null && user.getRole() == UserRole.STUDENT) {
            return user;
        } else {
            throw new EntityNotFoundException(notFoundMessage(id));
        }
    }

    public List<User> getTeachers(List<String> teacherIds) {
        return teacherIds.stream().map(this::getTeacherById).toList();
    }

    public List<User> getStudents(List<String> studentIds) {
        return studentIds.stream().map(this::getStudentById).toList();
    }

    @Override
    public User convertToEntity(UserDto dto) {
        return null;
    }

    public UserDto convertToDto(User user) {
        UserDto dto = new UserDto(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getName(),
                user.getRole().toString(),
                null,
                null,
                null

        );
        if (user.getRole() == UserRole.TEACHER
                && user.getClassesToTeach() != null
                && user.getSubjectsToTeach() != null) {
            dto.setClassToTeachIds(user.getClassesToTeach().stream().map(SchoolClass::getId).toList());
            dto.setSubjectToTeachIds(user.getSubjectsToTeach().stream().map(Subject::getId).toList());
        }
        if (user.getRole() == UserRole.STUDENT
                && user.getGroup() != null) {
            dto.setGroupId(user.getGroup().getId());
        }
        return dto;
    }

    @Override
    public List<User> getByIds(List<String> ids) {
        return null;
    }

    @Override
    public List<String> getIds(List<User> entities) {
        return null;
    }

    public List<String> getStudentIds(List<User> students) {
        return students.stream().map(this::getStudentId).toList();
    }

    private String getStudentId(User user) {
        if (user.getRole() == UserRole.STUDENT) {
            return user.getId();
        } else {
            throw new EntityNotFoundException(notFoundMessage(user.getId()));
        }
    }

    public List<UserDto> getAllTeachers() {
        return repository.findAll().stream().filter(user -> user.getRole() == UserRole.TEACHER).map(this::convertToDto).toList();
    }

    public List<UserDto> getAllStudents() {
        return repository.findAll().stream().filter(user -> user.getRole() == UserRole.STUDENT).map(this::convertToDto).toList();
    }
}