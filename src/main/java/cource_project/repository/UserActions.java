package cource_project.repository;

import cource_project.models.UserAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserActions extends JpaRepository<UserAction, Integer> {
}
