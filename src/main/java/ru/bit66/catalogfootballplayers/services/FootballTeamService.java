package ru.bit66.catalogfootballplayers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bit66.catalogfootballplayers.entitites.FootballTeam;
import ru.bit66.catalogfootballplayers.exceptions.FootballTeamNotFoundException;
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

    public void saveFootballTeam(FootballTeam footballTeam) {
        if (!footballTeamRepository.existsByName(footballTeam.getName())) {
            footballTeamRepository.save(footballTeam);
        }
    }

    public List<FootballTeam> getAllFootballTeams() {
        return footballTeamRepository.findAll();
    }

    public FootballTeam getFootballTeamById(Long id) throws FootballTeamNotFoundException {
        Optional<FootballTeam> footballTeam = footballTeamRepository.findById(id);
        if (footballTeam.isPresent()) {
            return footballTeam.get();
        }
        throw new FootballTeamNotFoundException("Team not found!");
    }

    public Boolean isThereTeamByName(String name) {
        return footballTeamRepository.existsByName(name);
    }
}
