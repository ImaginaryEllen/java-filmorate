package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BaseFilmService implements FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final GenreStorage genreStorage;

    @Override
    public Film create(Film film) {
        final Film newFilm = filmStorage.create(film);
        if (filmStorage.getById(newFilm.getId()) == null) {
            throw new NotFoundException("Create ERROR film : " + film);
        }
        return newFilm;
    }

    @Override
    public Film update(Film film) {
        validationFilm(film.getId());
        return filmStorage.update(film);
    }

    @Override
    public List<Film> getList() {
        return filmStorage.getList();
    }

    @Override
    public Film getById(Long id) {
        validationFilm(id);
        return filmStorage.getById(id);
    }

    @Override
    public void addLike(Long filmId, Long userId) {
        validationLike(filmId, userId);
        filmStorage.addLike(filmId, userId);
    }

    @Override
    public void deleteLike(Long filmId, Long userId) {
        validationLike(filmId, userId);
        filmStorage.deleteLike(filmId, userId);
    }

    @Override
    public List<Film> getPopularFilms(int count) {
        return filmStorage.getPopularFilms(count);
    }

    @Override
    public void addGenresToFilm(Long filmId, Long genreId) {
        if (genreStorage.getGenreById(genreId) == null) {
            throw new NotFoundException("Genre with ID: " + genreId + " not found");
        }
        filmStorage.addGenreToFilm(filmId, genreId);
    }

    private void validationFilm(Long id) {
        if (filmStorage.getById(id) == null) {
            throw new NotFoundException("Film with ID: " + id + " not found");
        }
    }

    private void validationLike(Long filmId, Long userId) {
        if (userStorage.getById(userId) == null) {
            throw new NotFoundException("User with ID: " + userId + " not found");
        }
        if (filmStorage.getById(filmId) == null) {
            throw new NotFoundException("Film with ID: " + filmId + " not found");
        }
    }
}
