package ru.edu.schedule_app.entities.group;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.edu.schedule_app.entities.school_class.SchoolClass;
import ru.edu.schedule_app.entities.user.User;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private int number;

    @ManyToOne
    private List<User> students;

    @ManyToMany
    private List<SchoolClass> classes;
}
