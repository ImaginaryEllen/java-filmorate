package ru.yandex.practicum.filmorate.repository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.FilmException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
@Component
public class FilmRepository {
	private long idNumber;
	private Map<Long, Film> films = new HashMap<>();

	public Film createFilm(@Valid @RequestBody Film film) {
		film.setId(++idNumber);
		films.put(film.getId(), film);
		return film;
	}

	public Film updateFilm(@Valid @RequestBody Film film) {
		if (!films.containsKey(film.getId())) {
			log.warn("Film does not exist: {}", film);
			throw new FilmException("Not found Film with id : " + film.getId());
		}
		films.put(film.getId(), film);
		return film;
	}
}
