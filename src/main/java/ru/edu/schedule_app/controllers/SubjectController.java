package ru.edu.schedule_app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.edu.schedule_app.entities.subject.SubjectDto;
import ru.edu.schedule_app.services.SubjectService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/subject")
public class SubjectController {

    private final SubjectService service;

    @PostMapping("/create")
    public ResponseEntity<SubjectDto> createSubject(@RequestBody SubjectDto createdSubject) {
        return ResponseEntity.ok(service.createSubject(createdSubject));
    }

    @DeleteMapping("/delete")
    public void deleteSubject(@PathVariable String id) {
        service.deleteSubject(id);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<SubjectDto>> getAllSubjects() {
        return ResponseEntity.ok(service.getAllSubjects());
    }

    @PatchMapping("/edit")
    public ResponseEntity<SubjectDto> editSubject(@RequestBody SubjectDto editedSubject) {
        return ResponseEntity.ok(service.editSubject(editedSubject));
    }
}
