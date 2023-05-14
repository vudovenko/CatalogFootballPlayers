package ru.bit66.catalogfootballplayers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.bit66.catalogfootballplayers.entitites.Footballer;
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
    public String pageForAddingFootballer(Model model) {
        model.addAttribute("footballer", new Footballer());
        model.addAttribute("footballTeams", footballTeamService.getAllFootballTeams());
        return "/addingFootballers";
    }

    @PostMapping("/saveFootballer")
    public String addNewFootballer(Footballer footballer) {
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
    public String editFootballer(Model model, @PathVariable("id") Long id) {
        Footballer footballer = footballerService.getFootballerById(id);
        model.addAttribute("footballer",
                footballer);
        model.addAttribute("footballTeams",
                footballTeamService.getAllFootballTeams());
        return "/addingFootballers";
    }
}
