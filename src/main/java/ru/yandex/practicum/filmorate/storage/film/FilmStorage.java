package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Like;

import java.util.List;

public interface FilmStorage {

    Film create(Film film);

    Film update(Film film);

    List<Film> getList();

    Film getById(Long id);

    void addLike(Long filmId, Long userId);

    Like getLike(Long filmId, Long userId);

    void deleteLike(Long filmId, Long userId);

    List<Film> getPopularFilms(int count);

    void addGenreToFilm(Long filmId, Long genreId);

    List<Long> getGenresIdByFilm(Long id);

}
