package ru.bit66.catalogfootballplayers.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.bit66.catalogfootballplayers.exceptions.FootballPlayerNotFoundException;
import ru.bit66.catalogfootballplayers.exceptions.FootballTeamNotFoundException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(FootballPlayerNotFoundException.class)
    public String handleFootballPlayerNotFoundException
            (FootballPlayerNotFoundException ex) {
        return "redirect:/footballers?message=" + ex.getMessage();
    }

    @ExceptionHandler(FootballTeamNotFoundException.class)
    public String handleFootballTeamNotFoundException
            (FootballTeamNotFoundException ex) {
        return "redirect:/footballers?message=" + ex.getMessage();
    }
}
