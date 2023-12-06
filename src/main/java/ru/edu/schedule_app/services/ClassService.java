package ru.edu.schedule_app.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.edu.schedule_app.entities.school_class.SchoolClass;
import ru.edu.schedule_app.repositories.ClassRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassService {

    private final ClassRepository repository;

    public SchoolClass getClassById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Class with id " + id + "was not found"));
    }

    public List<SchoolClass> getClasses(List<String> classesIds) {
        return classesIds.stream().map(this::getClassById).toList();
    }

    public List<String> getClassIds(List<SchoolClass> classes) {
        return classes.stream().map(SchoolClass::getId).toList();
    }
}
