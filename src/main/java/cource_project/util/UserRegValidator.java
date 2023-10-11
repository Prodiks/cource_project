package cource_project.util;

import cource_project.dto.UserDTO;
import cource_project.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserRegValidator implements Validator {

    private final UsersService usersService;

    @Autowired
    public UserRegValidator(UsersService usersService) {
        this.usersService = usersService;
    }
    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO user = (UserDTO) target;

        if(usersService.getUserByUsername(user.getEmail()).isPresent()){
            errors.rejectValue("email", "", "Человек с таким email уже существует");
        }
    }
}
