package ru.bit66.catalogfootballplayers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String addFootballers(Model model) {
        model.addAttribute("footballer", new Footballer());
        model.addAttribute("footballTeams", footballTeamService.getAllFootballTeams());
        return "/addingFootballers";
    }

    @PostMapping("/addNewFootballer")
    public String addNewFootballer(Footballer footballer) {
        footballerService.saveFootballer(footballer, footballTeamService);
        return "redirect:/footballers";
    }
}
