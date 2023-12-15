package ru.edu.schedule_app.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.edu.schedule_app.entities.school_class.SchoolClass;
import ru.edu.schedule_app.entities.subject.Subject;
import ru.edu.schedule_app.entities.subject.SubjectDto;
import ru.edu.schedule_app.entities.user.User;
import ru.edu.schedule_app.repositories.SubjectRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository repository;
    private final UserService userService;
    private final ClassService classService;

    private SubjectDto convertToDto(Subject subject) {
        SubjectDto dto = new SubjectDto(
                subject.getId(),
                subject.getName(),
                null,
                null
        );
        if (!subject.getTeachers().isEmpty()) {
            dto.setTeacherIds(getTeacherIds(subject.getTeachers()));
        }
        if (!subject.getClasses().isEmpty()) {
            dto.setTeacherIds(getClassIds(subject.getClasses()));
        }
        return dto;
    }

    private Subject convertToEntity(SubjectDto dto) {
        Subject subject = new Subject(
                dto.getId(),
                dto.getName(),
                null,
                null
        );
        if (!dto.getTeacherIds().isEmpty()) {
            List<User> teachers = new ArrayList<>();
            for (int i = 0; i < dto.getTeacherIds().size(); i++) {
                teachers.add(userService.getTeacherById(dto.getClassIds().get(i)));
            }
            subject.setTeachers(teachers);
        }
        return subject;
    }

    private List<String> getClassIds(List<SchoolClass> classes) {
        return classes.stream().map(SchoolClass::getId).toList();
    }

    private List<String> getTeacherIds(List<User> teachers) {
        return teachers.stream().map(User::getId).toList();
    }

    public SubjectDto createSubject(SubjectDto createdSubject) {
        Subject subject = repository.save(convertToEntity(createdSubject));
        return convertToDto(subject);
    }

    public void deleteSubject(String id) {
        repository.delete(getSubjectById(id));
    }

    private Subject getSubjectById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subject with id " + " was not found"));
    }

    public List<SubjectDto> getAllSubjects() {
        return repository.findAll().stream().map(this::convertToDto).toList();
    }

    private List<User> getTeachers(List<String> teacherIds) {
        return teacherIds.stream().map(userService::getTeacherById).toList();
    }

    private List<SchoolClass> getClasses(List<String> classesIds) {
        return classesIds.stream().map(classService::getClassesById).toList();
    }

    public SubjectDto editSubject(SubjectDto editedSubject) {
        String name = editedSubject.getName();
        List<String> teacherIds = editedSubject.getTeacherIds();
        List<String> classesIds = editedSubject.getClassIds();
        Subject subject = getSubjectById(editedSubject.getId());
        if (name != null && !name.isEmpty()) {
            subject.setName(name);
        }
        subject.setTeachers(getTeachers(teacherIds));
        subject.setClasses(getClasses(classesIds));
        return convertToDto(repository.save(subject));
    }
}
