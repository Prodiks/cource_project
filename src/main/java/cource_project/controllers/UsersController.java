package ru.roman.courseproject.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.roman.courseproject.models.User;
import ru.roman.courseproject.service.ApartmentService;
import ru.roman.courseproject.service.UserActionsService;
import ru.roman.courseproject.service.UsersService;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;
    private final ApartmentService apartmentService;

    private final UserActionsService userActionsService;

    @Autowired
    public UsersController(UsersService usersService, ApartmentService apartmentService, UserActionsService userActionsService) {
        this.usersService = usersService;
        this.apartmentService = apartmentService;
        this.userActionsService = userActionsService;
    }

    @GetMapping()
    public String index(Model model){
        userActionsService.writeLog("Переход на сылку /users");
        model.addAttribute("users", usersService.findAll());
        return "users/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        userActionsService.writeLog("Переход на сылку /users/" + id);
        model.addAttribute("user", usersService.findOne(id));
        model.addAttribute("apartments", usersService.getApartmentsByUserId(id));
        return "users/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        userActionsService.writeLog("Переход на сылку /users/" + id + "/edit");
        model.addAttribute("user", usersService.findOne(id));
        return "users/edit";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user,
                         @PathVariable("id") int id,
                         BindingResult bindingResult,
                         Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("user", usersService.findOne(id));
            return "users/edit";
        }

        userActionsService.writeLog("Изменение пользователя с id=" + id);
        usersService.update(id, user);
        return "redirect:/users/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        userActionsService.writeLog("Удаление пользователя с id=" + id);
        usersService.delete(id);
        return "redirect:/users";
    }
}
