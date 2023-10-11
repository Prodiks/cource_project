package ru.roman.courseproject.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.roman.courseproject.models.Apartment;
import ru.roman.courseproject.models.User;
import ru.roman.courseproject.service.ApartmentService;
import ru.roman.courseproject.service.UserActionsService;
import ru.roman.courseproject.service.UsersService;

@Controller
@RequestMapping("/apartments")
public class ApartmentController {
    private final ApartmentService apartmentService;
    private final UsersService usersService;

    private final UserActionsService userActionsService;

    @Autowired
    public ApartmentController(ApartmentService apartmentService, UsersService usersService, UserActionsService userActionsService) {
        this.apartmentService = apartmentService;
        this.usersService = usersService;
        this.userActionsService = userActionsService;
    }

    @GetMapping()
    public String index(Model model,
                        @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "apartments_per_page", required = false) Integer apartmentsPerPage){
        userActionsService.writeLog("Переход на сылку /apartments");

        if(page == null || apartmentsPerPage == null)
            model.addAttribute("apartments", apartmentService.findAll());
        else
            model.addAttribute("apartments", apartmentService.findWithPagination(page, apartmentsPerPage));
        return "apartments/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model,
                       @ModelAttribute("user") User user){
        model.addAttribute("apartment", apartmentService.findOne(id));

        User apartmentOwner = apartmentService.getApartmentOwner(id);

        userActionsService.writeLog("Переход на сылку /apartments/" + id);

        if(apartmentOwner != null)
            model.addAttribute("owner", apartmentOwner);
        else
            model.addAttribute("users", usersService.findAll());

        return "apartments/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id,
                       Model model){
        userActionsService.writeLog("Переход на сылку /apartments/" + id + "/edit");
        model.addAttribute("apartment", apartmentService.findOne(id));
        return "apartments/edit";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("apartment") Apartment apartment, @PathVariable("id") int id,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "apartments/edit";

        userActionsService.writeLog("Изменение книги с id=" + id);
        apartmentService.update(id, apartment);
        return "redirect:/apartments/" + id;
    }

    @GetMapping("/new")
    public String newApartment(@ModelAttribute("apartment") Apartment apartment){
        userActionsService.writeLog("Переход на сылку /apartments/new");
        return "apartments/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("apartment") @Valid Apartment apartment,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "apartments/new";

        userActionsService.writeLog("Добавление новой книги");
        apartmentService.save(apartment);
        return "redirect:/apartments";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        userActionsService.writeLog("Удаление книги с id=" + id);
        apartmentService.delete(id);
        return "redirect:/apartments";
    }

    @PostMapping("/{id}/release")
    public String release(@PathVariable("id") int id){
        userActionsService.writeLog("Освобождение книги");
        apartmentService.release(id);
        return "redirect:/apartments/" + id;
    }

    @PostMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id,
                         @ModelAttribute("user") User user){
        userActionsService.writeLog("Занятие книги");
        apartmentService.assign(id, user);
        return "redirect:/apartments/" + id;
    }

//    @GetMapping("/search")
//    public String search(){
//        userActionsService.writeLog("Переход на сылку /apartments/search");
//        return "apartments/search";
//    }

//    @PostMapping("/search")
//    public String doSearch(Model model, @RequestParam("query") String query){
//        userActionsService.writeLog("Поиск книги, запрос: " + query);
//        model.addAttribute("apartments", apartmentsService.searchByTitle(query));
//        return "apartments/search";
//    }
}
