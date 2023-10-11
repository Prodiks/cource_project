package cource_project.security;

import cource_project.dto.UserDTO;
import cource_project.models.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDTO userDTO);

    User findUserByUsername(String username);

    User findUserByEmail(String email);

    List<UserDTO> findAllUsers();
}
