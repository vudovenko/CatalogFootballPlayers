package ru.bit66.catalogfootballplayers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.bit66.catalogfootballplayers.entitites.FootballTeam;
import ru.bit66.catalogfootballplayers.entitites.Footballer;
import ru.bit66.catalogfootballplayers.exceptions.FootballPlayerNotFoundException;
import ru.bit66.catalogfootballplayers.exceptions.FootballTeamNotFoundException;
import ru.bit66.catalogfootballplayers.repositories.FootballerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FootballerService {

    private final FootballerRepository footballerRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public FootballerService(FootballerRepository footballerRepository,
                            SimpMessagingTemplate messagingTemplate) {
        this.footballerRepository = footballerRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public void saveFootballer(Footballer footballer,
                               FootballTeamService footballTeamService)
            throws FootballPlayerNotFoundException, FootballTeamNotFoundException {
        FootballTeam footballTeam = getOrCreateFootballTeam(footballer, footballTeamService);
        footballer.setTeam(footballTeam);
        if (footballer.getId() != null) {
            updateFootballer(footballer);
            return;
        }
        footballerRepository.save(footballer);
        messagingTemplate.convertAndSend("/topic/public", getAllFootballers());
    }

    private static FootballTeam getOrCreateFootballTeam(Footballer footballer,
                                                        FootballTeamService footballTeamService)
            throws FootballTeamNotFoundException {
        FootballTeam footballTeam = footballer.getTeam();
        if (footballTeam == null) {
            footballTeam = createNewTeam(footballer, footballTeamService);
        } else {
            footballTeam = footballTeamService
                    .getFootballTeamById(footballer.getTeam().getId());
        }
        return footballTeam;
    }

    private static FootballTeam createNewTeam(Footballer footballer,
                                              FootballTeamService footballTeamService) {
        FootballTeam footballTeam;
        footballTeam = new FootballTeam();
        footballTeam.setName(footballer.getNewEnteredTeam());
        footballTeamService.saveFootballTeam(footballTeam);
        return footballTeam;
    }

    public List<Footballer> getAllFootballers() {
        return footballerRepository.findAll();
    }

    public void updateFootballer(Footballer footballer) throws FootballPlayerNotFoundException {
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
            footballerRepository.save(updatedFootballer);
            messagingTemplate.convertAndSend("/topic/public", getAllFootballers());
            return;
        }
        throw new FootballPlayerNotFoundException("Football player not found!");
    }

    public Footballer getFootballerById(Long id) throws FootballPlayerNotFoundException {
        Optional<Footballer> footballer = footballerRepository.findById(id);
        if (footballer.isPresent()) {
            return footballer.get();
        }
        throw new FootballPlayerNotFoundException("Football player not found!");
    }

    public Boolean checkConditionsAndAddMessageToModel(Footballer footballer,
                                                       FootballTeamService footballTeamService,
                                                       Model model) {
        if (footballer.getTeam() == null
                && (footballer.getNewEnteredTeam() == null
                || footballer.getNewEnteredTeam().isEmpty())) {
            model.addAttribute("teamMessage", "Выберите команду!");
            return true;
        } else if (footballer.getNewEnteredTeam() != null && !footballer.getNewEnteredTeam().isEmpty()) {
            if (footballTeamService.isThereTeamByName(footballer.getNewEnteredTeam())) {
                model.addAttribute("teamExistsMessage", "Такая команда уже существует!");
                return true;
            } else if (footballer.getNewEnteredTeam().matches("^\\s*$")) {
                model.addAttribute("spacesMessage", "Имя команды не должно быть целиком из пробелов!");
                return true;
            }
        }
        return false;
    }
}
