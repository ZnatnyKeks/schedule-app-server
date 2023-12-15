package ru.edu.schedule_app.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.edu.schedule_app.entities.school_class.SchoolClass;
import ru.edu.schedule_app.repositories.ClassRepository;

@Service
@RequiredArgsConstructor
public class ClassService {

    private final ClassRepository repository;

    public SchoolClass getClassesById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Class with id " + id + "was not found"));
    }
}
