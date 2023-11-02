package ru.bit66.catalogfootballplayers.entitites;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @NotBlank
    @Size(min = 2, max = 30) // todo вывод именно той ошибки, которая указана в message
    @Pattern(regexp = "^[^0-9]*$", message = "Имя не должно содержать цифры")
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 30)
    @Pattern(regexp = "^[^0-9]*$", message = "Имя не должно содержать цифры")
    private String lastName;
    @NotNull
    private Date birthDate;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @NotNull
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
