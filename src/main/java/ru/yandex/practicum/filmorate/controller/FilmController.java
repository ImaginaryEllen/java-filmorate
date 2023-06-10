package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.FilmRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/films")
@Validated
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class FilmController {
	private FilmRepository repository = new FilmRepository();

	@GetMapping()
	public List<Film> getFilms() {
		return new ArrayList<>(repository.getFilms().values());
	}

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public Film createFilm(@Valid @RequestBody Film film) {
		log.info("Create film: {} - STARTED", film);
		film = repository.createFilm(film);
		log.info("Create film: {} - FINISHED", film);
		return film;
	}

	@PutMapping()
	public Film update(@Valid @RequestBody Film film) {
		log.info("Update film: {} - STARTED", film);
		film = repository.updateFilm(film);
		log.info("Update film: {} - FINISHED", film);
		return film;
	}
}
