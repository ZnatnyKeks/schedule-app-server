package ru.edu.schedule_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edu.schedule_app.entities.group.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, String> {
}
