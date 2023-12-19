package ru.edu.schedule_app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.edu.schedule_app.entities.user.User;
import ru.edu.schedule_app.entities.user.UserDto;
import ru.edu.schedule_app.services.UserService;

import java.util.List;

@RestController
@RequestMapping("api/v1/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final UserService service;

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity.ok(service.getAllTeachers());
    }

    @GetMapping("/one")
    public ResponseEntity<UserDto> getOneTeacher(@RequestParam String id) {
        User teacher = service.getById(id);
        return ResponseEntity.ok(service.convertToDto(teacher));
    }
}
