package ru.edu.schedule_app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.edu.schedule_app.entities.group.GroupDto;
import ru.edu.schedule_app.services.GroupService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/group")
public class GroupController {

    private final GroupService service;

    @PostMapping("/create")
    public ResponseEntity<GroupDto> createGroup(@RequestBody  GroupDto createdGroup) {
        return ResponseEntity.ok(service.createGroup(createdGroup));
    }

    @DeleteMapping("/delete")
    public void deleteGroup(@PathVariable String id) {
        service.deleteGroup(id);
    }

    @PatchMapping("/edit")
    public ResponseEntity<GroupDto> editGroup(@RequestBody GroupDto editedGroup) {
        return ResponseEntity.ok(service.editGroup(editedGroup));
    }
    @GetMapping("/all")
    public ResponseEntity<List<GroupDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }


}
