package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.validator.ReleaseDateConstraint;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Film extends Entity {
	@NotBlank
	private String name;
	@Size(max = 200)
	private String description;
	@ReleaseDateConstraint
	private LocalDate releaseDate;
	@Min(1)
	private int duration;
}
