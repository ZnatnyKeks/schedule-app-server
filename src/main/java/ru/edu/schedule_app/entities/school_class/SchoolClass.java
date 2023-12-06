package ru.edu.schedule_app.entities.school_class;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.edu.schedule_app.entities.group.Group;
import ru.edu.schedule_app.entities.subject.Subject;
import ru.edu.schedule_app.entities.user.User;

import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolClass {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    private Weekday weekday;

    private LocalTime time;

    @ManyToOne
    private Subject subject;

    @ManyToOne
    private User teacher;

    @ManyToMany
    private List<Group> groups;
}
