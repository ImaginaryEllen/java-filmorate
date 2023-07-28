package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class User extends Entity {
    @NotBlank
    @Email
    private String email;
    @Pattern(regexp = "\\S+")
    @NotBlank
    private String login;
    private String name;
    @Past
    private LocalDate birthday;

    public User(Long id, String email, String login, String name, LocalDate birthday) {
        super(id);
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    public User(String email, String login, String name, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }
}
