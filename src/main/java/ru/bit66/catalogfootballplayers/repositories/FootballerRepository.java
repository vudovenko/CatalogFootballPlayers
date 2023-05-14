package ru.bit66.catalogfootballplayers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bit66.catalogfootballplayers.entitites.Footballer;

public interface FootballerRepository extends JpaRepository<Footballer, Long> {
}
