package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmService {

	Film create(Film film);

	Film update(Film film);

	List<Film> getList();

	void addLike(long filmId, long userId);

	void deleteLike(long filmId, long userId);

	List<Film> getPopularFilms(int count);

	Film getById(long id);
}
