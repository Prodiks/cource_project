package cource_project.controllers;

import cource_project.models.Apartment;
import cource_project.models.User;
import cource_project.service.ApartmentService;
import cource_project.service.UserActionsService;
import cource_project.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

        userActionsService.writeLog("Изменение квартиры с id=" + id);
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

        userActionsService.writeLog("Добавление новой квартиры");
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
        userActionsService.writeLog("Освобождение квартиры");
        apartmentService.release(id);
        return "redirect:/apartments/" + id;
    }

    @PostMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id,
                         @ModelAttribute("user") User user){
        userActionsService.writeLog("Занятие квартиры");
        apartmentService.assign(id, user);
        return "redirect:/apartments/" + id;
    }
}
