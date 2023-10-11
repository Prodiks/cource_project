package ru.roman.courseproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.roman.courseproject.service.UserActionsService;

@Controller
@RequestMapping("/")
public class MainController {

    private final UserActionsService userActionsService;

    @Autowired
    public MainController(UserActionsService userActionsService) {
        this.userActionsService = userActionsService;
    }

    @GetMapping()
    public String hello(){
        userActionsService.writeLog("Переход на главную страницу");
        return "HelloPage";
    }
}
