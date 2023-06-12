package ru.yandex.practicum.filmorate.repository;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;

@NoArgsConstructor
@Component
public class FilmRepository extends Repository<Film> {

	@Override
	public Film create(@Valid @RequestBody Film film) {
		film.setId(++idNumber);
		all.put(film.getId(), film);
		return film;
	}
}
