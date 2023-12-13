package com.ru.dating.repositories;

import com.ru.dating.entities.hobby.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HobbyRepository extends JpaRepository<Hobby, String> {
}
