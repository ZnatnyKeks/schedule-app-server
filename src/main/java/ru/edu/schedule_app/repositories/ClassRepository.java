package ru.edu.schedule_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edu.schedule_app.entities.school_class.SchoolClass;

@Repository
public interface ClassRepository extends JpaRepository<SchoolClass, String> {
}
