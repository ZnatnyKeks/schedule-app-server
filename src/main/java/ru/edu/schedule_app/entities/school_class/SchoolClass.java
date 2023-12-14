package ru.edu.schedule_app.entities.school_class;

import ru.edu.schedule_app.entities.subject.Subject;
import ru.edu.schedule_app.entities.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

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

    @OneToMany
    private Subject subject;

    @OneToMany
    private User teacher;
}
