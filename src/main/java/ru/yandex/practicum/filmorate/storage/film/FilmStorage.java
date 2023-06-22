package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FilmStorage {

	Film create(Film film);

	Film update(Film film);

	List<Film> getList();

	Film getById(long id);

	void addLike(Film film, User user);

	void deleteLike(Film film, User user);

	List<Film> getPopularFilms(int count);
}
