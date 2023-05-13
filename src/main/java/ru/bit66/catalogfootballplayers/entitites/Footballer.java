package ru.bit66.catalogfootballplayers.entitites;

import jakarta.persistence.*;
import lombok.*;
import ru.bit66.catalogfootballplayers.enums.Country;
import ru.bit66.catalogfootballplayers.enums.Gender;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "footballers")
public class Footballer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private Country country;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.REFRESH, CascadeType.MERGE},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id")
    private FootballTeam team;
    @Transient
    private String newEnteredTeam;
}
