package ru.edu.schedule_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edu.schedule_app.entities.subject.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, String> {
}
