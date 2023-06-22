package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BaseFilmService implements FilmService {
	private final FilmStorage filmStorage;
	private final UserStorage userStorage;

	@Override
	public Film create(Film film) {
		return filmStorage.create(film);
	}

	@Override
	public Film update(Film film) {
		return filmStorage.update(film);
	}

	@Override
	public List<Film> getList() {
		return filmStorage.getList();
	}

	@Override
	public Film getById(long id) {
		final Film film = filmStorage.getById(id);
		if (film == null) {
			throw new NotFoundException("Film with id: " + id + " not found");
		}
		return film;
	}

	@Override
	public void addLike(long filmId, long userId) {
		User user = userStorage.getById(userId);
		if (user == null) {
			throw new NotFoundException("User with id: " + userId + " not found");
		}
		Film film = getById(filmId);
		filmStorage.addLike(film, user);
	}

	@Override
	public void deleteLike(long filmId, long userId) {
		User user = userStorage.getById(userId);
		if (user == null) {
			throw new NotFoundException("User with id: " + userId + " not found");
		}
		Film film = getById(filmId);
		filmStorage.deleteLike(film, user);
	}

	@Override
	public List<Film> getPopularFilms(int count) {
		return filmStorage.getPopularFilms(count);
	}
}
