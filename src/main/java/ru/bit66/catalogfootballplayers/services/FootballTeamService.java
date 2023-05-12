package ru.bit66.catalogfootballplayers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bit66.catalogfootballplayers.entitites.FootballTeam;
import ru.bit66.catalogfootballplayers.repositories.FootballTeamRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FootballTeamService {

    private final FootballTeamRepository footballTeamRepository;

    @Autowired
    public FootballTeamService(FootballTeamRepository footballTeamRepository) {
        this.footballTeamRepository = footballTeamRepository;
    }

    public FootballTeam saveFootballTeam(FootballTeam footballTeam) {
        return footballTeamRepository.save(footballTeam);
    }

    public List<FootballTeam> getAllFootballTeams() {
        return footballTeamRepository.findAll();
    }

    public FootballTeam getFootballTeamById(Long id) {
        return footballTeamRepository.findById(id).orElse(null);
        // todo добавить исключение ненайденной команды
    }

    public FootballTeam updateFootballTeam(FootballTeam footballTeam) {
        Optional<FootballTeam> existingFootballTeam
                = footballTeamRepository.findById(footballTeam.getId());
        if (existingFootballTeam.isPresent()) {
            FootballTeam updatedFootballTeam = existingFootballTeam.get();
            updatedFootballTeam.setName(existingFootballTeam.get().getName());
            return footballTeamRepository.save(updatedFootballTeam);
        }

        return null; // todo добавить исключение ненайденной команды
    }
}
