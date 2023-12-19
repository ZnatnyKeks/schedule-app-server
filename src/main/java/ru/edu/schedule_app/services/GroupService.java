package ru.edu.schedule_app.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.edu.schedule_app.entities.group.Group;
import ru.edu.schedule_app.entities.group.GroupDto;
import ru.edu.schedule_app.entities.school_class.SchoolClass;
import ru.edu.schedule_app.entities.user.User;
import ru.edu.schedule_app.repositories.GroupRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService implements EntityService<Group, GroupDto>{

    private final GroupRepository repository;
    private final ClassService classService;
    private final UserService userService;

    public GroupDto createGroup(GroupDto createdGroup) {
        return convertToDto(repository.save(convertToEntity(createdGroup)));
    }

    public GroupDto convertToDto(Group group) {
        GroupDto dto = new GroupDto(
                group.getId(),
                group.getNumber(),
                null,
                null
        );
        if (group.getStudents() != null) {
            dto.setStudentIds(userService.getStudentIds(group.getStudents()));
        }
        if (group.getClasses() != null) {
            dto.setClassIds(classService.getIds(group.getClasses()));
        }
        return dto;
    }

    @Override
    public Group getById(String id) {
        return null;
    }

    @Override
    public List<Group> getByIds(List<String> ids) {
        return null;
    }

    @Override
    public List<String> getIds(List<Group> groups) {
        return groups.stream().map(Group::getId).toList();

    }


    public Group convertToEntity(GroupDto dto) {
        Group group = new Group(
                null,
                dto.getNumber(),
                null,
                null
        );
        if (dto.getId() != null) {
            group.setId(dto.getId());
        }
        if (dto.getStudentIds() != null) {
            List<User> students = new ArrayList<>();
            for (String id : dto.getStudentIds()) {
                students.add(userService.getStudentById(id));
            }
            group.setStudents(students);
        }
        if (dto.getClassIds() != null) {
            List<SchoolClass> classes = new ArrayList<>();
            for (String id : dto.getClassIds()) {
                classes.add(classService.getById(id));
            }
            group.setClasses(classes);
        }
        return group;
    }

    public void deleteGroup(String id) {
        repository.delete(getGroupById(id));
    }

    private Group getGroupById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Group with id " + id + " was not found"));
    }

    public GroupDto editGroup(GroupDto editedGroup) {
        int number = editedGroup.getNumber();
        List<String> studentIds = editedGroup.getStudentIds();
        List<String> classIds = editedGroup.getClassIds();
        Group group = getGroupById(editedGroup.getId());
        group.setNumber(number);
        group.setStudents(userService.getStudents(studentIds));
        group.setClasses(classService.getByIds(classIds));
        return convertToDto(repository.save(group));
    }
}
