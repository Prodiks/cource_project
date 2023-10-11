package ru.roman.courseproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.roman.courseproject.models.UserAction;

@Repository
public interface UserActions extends JpaRepository<UserAction, Integer> {
}
