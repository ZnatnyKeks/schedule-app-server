package ru.edu.schedule_app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.edu.schedule_app.entities.school_class.SchoolClass;
import ru.edu.schedule_app.entities.subject.Subject;
import ru.edu.schedule_app.entities.subject.SubjectDto;
import ru.edu.schedule_app.services.ClassService;
import ru.edu.schedule_app.services.SubjectService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/subject")
public class SubjectController {

    private final SubjectService subjectService;
    private final ClassService classService;

    @PostMapping("/create")
    public ResponseEntity<SubjectDto> createSubject(@RequestBody SubjectDto createdSubject) {
        return ResponseEntity.ok(subjectService.createSubject(createdSubject));
    }

    @DeleteMapping("/delete")
    public void deleteSubject(@PathVariable String id) {
        subjectService.deleteSubject(id);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SubjectDto>> getAllSubjects() {
        return ResponseEntity.ok(subjectService.getAllSubjects());
    }
    @GetMapping("/one")
    public ResponseEntity<SubjectDto> getOneSubject(@RequestParam String id) {
        Subject subject = subjectService.getById(id);
        List<String> classIds = classService.getIds(subject.getClasses());
        return ResponseEntity.ok(subjectService.convertToDto(subject, classIds));
    }

    @PatchMapping("/edit")
    public ResponseEntity<SubjectDto> editSubject(@RequestBody SubjectDto editedSubject) {
        List<SchoolClass> classes = classService.getByIds(editedSubject.getClassIds());
        return ResponseEntity.ok(subjectService.editSubject(editedSubject, classes));
    }
}
