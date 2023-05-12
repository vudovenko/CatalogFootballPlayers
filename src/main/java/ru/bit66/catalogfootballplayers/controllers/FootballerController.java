package ru.bit66.catalogfootballplayers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.bit66.catalogfootballplayers.services.FootballTeamService;
import ru.bit66.catalogfootballplayers.services.FootballerService;

@Controller
public class FootballerController {

    private final FootballerService footballerService;
    private final FootballTeamService footballTeamService;

    @Autowired
    public FootballerController(FootballerService footballerService,
                                FootballTeamService footballTeamService) {
        this.footballerService = footballerService;
        this.footballTeamService = footballTeamService;
    }
}
