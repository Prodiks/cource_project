package ru.roman.courseproject.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.roman.courseproject.dto.UserDTO;
import ru.roman.courseproject.models.Role;
import ru.roman.courseproject.models.User;
import ru.roman.courseproject.repository.RoleRepository;
import ru.roman.courseproject.repository.UsersRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private final UsersRepository usersRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UsersRepository usersRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        user.setFullName(userDTO.getFullName());

        Role role = roleRepository.findByName("ROLE_ADMIN");

        if(role == null){
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        usersRepository.save(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return usersRepository.findByUsername(username).get();
    }

    @Override
    public User findUserByEmail(String email) {
        return usersRepository.findByEmail(email).get();
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<User> users = usersRepository.findAll();
        return users.stream().map(user -> mapToUserDTO(user)).collect(Collectors.toList());
    }

    private UserDTO mapToUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setFullName(user.getFullName());
        return userDTO;
    }

    private Role checkRoleExist(){
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }
}
