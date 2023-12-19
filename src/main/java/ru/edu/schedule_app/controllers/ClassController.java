package ru.edu.schedule_app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.edu.schedule_app.entities.school_class.SchoolClassDto;
import ru.edu.schedule_app.services.ClassService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/class")
@RequiredArgsConstructor
public class ClassController {

    private final ClassService classService;

    @GetMapping("/all")
    public ResponseEntity<List<SchoolClassDto>> getAll(@PathVariable int _limit) {
        return ResponseEntity.ok(classService.getAll(_limit));
    }

}
