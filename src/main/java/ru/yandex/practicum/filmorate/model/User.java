package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
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
}
