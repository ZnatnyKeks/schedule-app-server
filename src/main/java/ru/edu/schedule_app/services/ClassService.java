package ru.edu.schedule_app.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.edu.schedule_app.entities.group.Group;
import ru.edu.schedule_app.entities.school_class.SchoolClass;
import ru.edu.schedule_app.entities.school_class.SchoolClassDto;
import ru.edu.schedule_app.entities.school_class.Weekday;
import ru.edu.schedule_app.repositories.ClassRepository;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassService{

    private final ClassRepository repository;
    @Autowired
    @Lazy
    private final SubjectService subjectService;
    private final UserService userService;

    public SchoolClass convertToEntity(SchoolClassDto dto, List<Group> groups) {
        SchoolClass schoolClass = new SchoolClass(
                null,
                Weekday.valueOf(dto.getWeekday()),
                LocalTime.of(dto.getHour(), dto.getMinute()),
                subjectService.getById(dto.getSubjectId()),
                userService.getTeacherById(dto.getTeacherId()),
                groups
        );
        if(!dto.getId().isEmpty()) {
            schoolClass.setId(dto.getId());
        }
        return schoolClass;
    }

    public SchoolClassDto convertToDto(SchoolClass entity) {
        return new SchoolClassDto(
                entity.getId(),
                entity.getWeekday().toString(),
                entity.getTime().getHour(),
                entity.getTime().getMinute(),
                entity.getSubject().getId(),
                entity.getTeacher().getId(),
                entity.getGroups().stream().map(Group::getId).toList()
        );
    }

    public SchoolClass getById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Class with id " + id + "was not found"));
    }

    public List<SchoolClass> getByIds(List<String> classesIds) {
        return classesIds.stream().map(this::getById).toList();
    }


    public List<String> getIds(List<SchoolClass> classes) {
        return classes.stream().map(SchoolClass::getId).toList();
    }

    public List<SchoolClassDto> getAll(int limit) {
        return repository.findAll().stream().map(this::convertToDto).toList().subList(0, limit);
    }
    public List<SchoolClassDto> getAll() {
        return repository.findAll().stream().map(this::convertToDto).toList();
    }
}
