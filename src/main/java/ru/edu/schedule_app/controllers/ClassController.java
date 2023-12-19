package ru.edu.schedule_app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.edu.schedule_app.entities.group.Group;
import ru.edu.schedule_app.entities.school_class.SchoolClassDto;
import ru.edu.schedule_app.entities.user.User;
import ru.edu.schedule_app.services.ClassService;
import ru.edu.schedule_app.services.GroupService;
import ru.edu.schedule_app.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/class")
@RequiredArgsConstructor
public class ClassController {

    private final ClassService classService;
    private final UserService userService;
    private final GroupService groupService;

    @GetMapping("/all")
    public ResponseEntity<List<SchoolClassDto>> getAll(@RequestParam String userId) {
        User user = userService.getById(userId);
        return ResponseEntity.ok(classService.getAll(user));
    }
    @PostMapping("/create")
    public ResponseEntity<SchoolClassDto> getAll(@RequestBody SchoolClassDto dto) {
        List<Group> groups = groupService.getByIds(dto.getGroupIds());
        return ResponseEntity.ok(classService.create(dto, groups));
    }

}
