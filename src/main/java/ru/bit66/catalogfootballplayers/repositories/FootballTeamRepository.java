package ru.bit66.catalogfootballplayers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bit66.catalogfootballplayers.entitites.FootballTeam;

public interface FootballTeamRepository extends JpaRepository<FootballTeam, Long> {
    Boolean existsByName(String name);
}
