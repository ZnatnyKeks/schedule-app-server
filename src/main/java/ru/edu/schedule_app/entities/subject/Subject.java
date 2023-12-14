package ru.edu.schedule_app.entities.subject;

import ru.edu.schedule_app.entities.school_class.SchoolClass;
import ru.edu.schedule_app.entities.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    @ManyToOne
    private List<User> teachers;

    @ManyToOne
    private List<SchoolClass> classes;
}
