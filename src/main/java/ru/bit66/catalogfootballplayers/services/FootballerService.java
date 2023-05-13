package ru.bit66.catalogfootballplayers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bit66.catalogfootballplayers.entitites.FootballTeam;
import ru.bit66.catalogfootballplayers.entitites.Footballer;
import ru.bit66.catalogfootballplayers.repositories.FootballerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FootballerService {

    private final FootballerRepository footballerRepository;

    @Autowired
    public FootballerService(FootballerRepository footballerRepository) {
        this.footballerRepository = footballerRepository;
    }

    public Footballer saveFootballer(Footballer footballer,
                                     FootballTeamService footballTeamService) {
        FootballTeam footballTeam = footballer.getTeam();
        if (footballTeam == null) {
            footballTeam = new FootballTeam();
            footballTeam.setName(footballer.getNewEnteredTeam());
            footballTeam.setFootballers(new ArrayList<>());
            footballTeamService.saveFootballTeam(footballTeam);
        } else {
            footballTeam = footballTeamService
                    .getFootballTeamById(footballer.getTeam().getId());
        }
        footballer.setTeam(footballTeam);
        return footballerRepository.save(footballer);
    }

    public List<Footballer> getAllFootballers() {
        return footballerRepository.findAll();
    }

    public Footballer updateFootballer(Footballer footballer) {
        Optional<Footballer> existingFootballer
                = footballerRepository.findById(footballer.getId());
        if (existingFootballer.isPresent()) {
            Footballer updatedFootballer = existingFootballer.get();
            updatedFootballer.setFirstName(footballer.getFirstName());
            updatedFootballer.setLastName(footballer.getLastName());
            updatedFootballer.setGender(footballer.getGender());
            updatedFootballer.setBirthDate(footballer.getBirthDate());
            updatedFootballer.setTeam(footballer.getTeam());
            updatedFootballer.setCountry(footballer.getCountry());
            return footballerRepository.save(updatedFootballer);
        }
        return null; // todo добавить исключение ненайденного футболиста
    }
}