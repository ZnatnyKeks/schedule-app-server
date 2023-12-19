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
public class SubjectService{

    private final SubjectRepository repository;
    private final UserService userService;

    public SubjectDto convertToDto(Subject subject,List<String> classIds ) {
        SubjectDto dto = new SubjectDto(
                subject.getId(),
                subject.getName(),
                null,
                null
        );
        if (subject.getTeachers() != null) {
            dto.setTeacherIds(getTeacherIds(subject.getTeachers()));
        }
        if (subject.getClasses() != null) {
            dto.setClassIds(classIds);
        }
        return dto;
    }

    public Subject convertToEntity(SubjectDto dto) {
        Subject subject = new Subject(
                null,
                dto.getName(),
                null,
                null
        );
        if (dto.getId() != null) {
            subject.setId(dto.getId());
        }
        if (!dto.getTeacherIds().isEmpty()) {
            List<User> teachers = new ArrayList<>();
            for (String id : dto.getTeacherIds()) {
                teachers.add(userService.getTeacherById(id));
            }
            subject.setTeachers(teachers);
        }
        return subject;
    }


    private List<String> getTeacherIds(List<User> teachers) {
        return teachers.stream().map(User::getId).toList();
    }

    public SubjectDto createSubject(SubjectDto createdSubject) {
        Subject subject = repository.save(convertToEntity(createdSubject));
        return convertToDto(subject, createdSubject.getClassIds());
    }

    public void deleteSubject(String id) {
        repository.delete(getById(id));
    }

    public Subject getById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subject with id " + id + " was not found"));
    }

    public List<Subject> getByIds(List<String> ids) {
        return ids.stream().map(this::getById).toList();
    }

    public List<String> getIds(List<Subject> entities) {
        return entities.stream().map(Subject::getId).toList();
    }

    public List<SubjectDto> getAllSubjects() {
        List<Subject> subjects =  repository.findAll();
        List<SubjectDto> dtos = new ArrayList<>();
        for (Subject subject : subjects) {
            List<String> classIds = subject.getClasses().stream().map(SchoolClass::getId).toList();
            dtos.add(convertToDto(subject, classIds));
        }
        return dtos;
    }


    public SubjectDto editSubject(SubjectDto editedSubject, List<SchoolClass> classes) {
        String name = editedSubject.getName();
        List<String> teacherIds = editedSubject.getTeacherIds();
        Subject subject = getById(editedSubject.getId());
        if (name != null && !name.isEmpty()) {
            subject.setName(name);
        }
        subject.setTeachers(userService.getTeachers(teacherIds));
        subject.setClasses(classes);
        return convertToDto(repository.save(subject), editedSubject.getClassIds());
    }

    public List<String> getSubjectIds(List<SchoolClass> classes) {
        return classes.stream().map(SchoolClass::getId).toList();
    }
}
