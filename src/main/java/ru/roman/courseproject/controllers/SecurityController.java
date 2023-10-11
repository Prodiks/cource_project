package ru.roman.courseproject.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.roman.courseproject.dto.UserDTO;
import ru.roman.courseproject.models.User;
import ru.roman.courseproject.security.UserService;
import ru.roman.courseproject.util.UserRegValidator;

@Controller
@RequestMapping("/auth")
public class SecurityController {

    private final UserRegValidator userRegValidator;
    private final UserService userService;

    @Autowired
    public SecurityController(UserRegValidator userRegValidator, UserService userService) {
        this.userRegValidator = userRegValidator;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage(){
        return "security/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user")User user){
        return "security/register";
    }

    @PostMapping("/registration")
    public String performRegister(@ModelAttribute("user") @Valid UserDTO userDTO,
                                  BindingResult bindingResult,
                                  Model model){
        userRegValidator.validate(userDTO, bindingResult);

        if(bindingResult.hasErrors()){
            model.addAttribute("user", userDTO);
            return "security/register";
        }


        userService.saveUser(userDTO);

        return "redirect:security/login";
    }
}
