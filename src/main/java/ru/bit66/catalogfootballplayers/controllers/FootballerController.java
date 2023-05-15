package ru.bit66.catalogfootballplayers.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.bit66.catalogfootballplayers.entitites.Footballer;
import ru.bit66.catalogfootballplayers.exceptions.FootballPlayerNotFoundException;
import ru.bit66.catalogfootballplayers.exceptions.FootballTeamNotFoundException;
import ru.bit66.catalogfootballplayers.services.FootballTeamService;
import ru.bit66.catalogfootballplayers.services.FootballerService;

@Controller
@RequestMapping("/footballers")
public class FootballerController {

    private final FootballerService footballerService;
    private final FootballTeamService footballTeamService;

    @Autowired
    public FootballerController(FootballerService footballerService,
                                FootballTeamService footballTeamService) {
        this.footballerService = footballerService;
        this.footballTeamService = footballTeamService;
    }

    @GetMapping
    public String pageForAddingFootballer(@RequestParam(value = "message", required = false)
                                          String message, Model model) {
        model.addAttribute("message", message);
        model.addAttribute("footballer", new Footballer());
        model.addAttribute("footballTeams", footballTeamService.getAllFootballTeams());
        return "/addingFootballers";
    }

    @PostMapping("/saveFootballer")
    public String addNewFootballer(@Valid Footballer footballer,
                                   BindingResult bindingResult, Model model)
            throws FootballPlayerNotFoundException, FootballTeamNotFoundException {
        if (bindingResult.hasErrors()
                || footballerService.areConditionsSatisfied(footballer, footballTeamService)) {
            model.addAttribute("footballer", footballer);
            model.addAttribute("footballTeams", footballTeamService.getAllFootballTeams());
            footballerService.addMessageToModel(footballer, footballTeamService, model);
            return "/addingFootballers";
        }
        footballerService.saveFootballer(footballer, footballTeamService);
        return "redirect:/footballers";
    }

    @GetMapping("/allFootballers")
    public String getAllFootballers(Model model) {
        model.addAttribute("allFootballers",
                footballerService.getAllFootballers());
        model.addAttribute("footballTeams",
                footballTeamService.getAllFootballTeams());
        return "/allFootballers";
    }

    @GetMapping("/edit/{id}")
    public String editFootballer(Model model, @PathVariable("id") Long id)
            throws FootballPlayerNotFoundException {
        Footballer footballer = footballerService.getFootballerById(id);
        model.addAttribute("footballer",
                footballer);
        model.addAttribute("footballTeams",
                footballTeamService.getAllFootballTeams());
        return "/addingFootballers";
    }
}
