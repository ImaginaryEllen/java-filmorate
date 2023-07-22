package ru.yandex.practicum.filmorate.storage.film;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.storage.genre.GenreDbStorage;
import ru.yandex.practicum.filmorate.storage.rating.RatingDbStorage;
import ru.yandex.practicum.filmorate.storage.rating.RatingStorage;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import({ FilmDbStorage.class, RatingDbStorage.class, GenreDbStorage.class })
class FilmDbStorageTest {

    @Autowired
    private FilmStorage filmStorage;
    @Autowired
    private RatingStorage ratingStorage;

    @Test
    void shouldCreateFilm() {
        Film film = filmStorage.create(new Film("Harry Potter", "d".repeat(100),
                LocalDate.of(2002, 3, 21), 120, ratingStorage.getRatingById(2L)));

        assertNotNull(film, "Create return empty film");
        assertEquals(6L, film.getId(),
                "Incorrect create film id: " + film.getId() + " but should be: " + 6L);
        assertEquals("Harry Potter", film.getName(),
                "Incorrect create film name: " + film.getName() + " but should be: " + "Harry Potter");
        assertEquals("d".repeat(100), film.getDescription(), "Incorrect create film description: " +
                film.getDescription() + " but should be: " + "d".repeat(100));
        assertEquals(LocalDate.of(2002, 3, 21), film.getReleaseDate(),
                "Incorrect create film release date: " + film.getReleaseDate() + " but should be: " +
                        LocalDate.of(2002, 3, 21));
        assertEquals(120, film.getDuration(),
                "Incorrect create film duration: " + film.getDuration() + " but should be: " + 120);
        assertEquals(2L, film.getMpa().getId(),
                "Incorrect create film rating id: " + film.getMpa().getId() + " but should be: " + 2L);
        assertEquals("PG", film.getMpa().getName(),
                "Incorrect create film rating name: " + film.getMpa().getName() + " but should be: " + "PG");
    }

    @Test
    void shouldUpdateFilmWithoutGenres() {
        Film film = filmStorage.update(new Film(1L, "Leon", "d".repeat(150),
                LocalDate.of(1984, 12, 14), 110, ratingStorage.getRatingById(5L)));

        assertNotNull(film, "Update return empty film");
        assertEquals(1L, film.getId(),
                "Incorrect update film id: " + film.getId() + " but should be: " + 1L);
        assertEquals("Leon", film.getName(),
                "Incorrect update film name: " + film.getName() + " but should be: " + "Leon");
        assertEquals("d".repeat(150), film.getDescription(), "Incorrect update film description: " +
                film.getDescription() + " but should be: " + "d".repeat(150));
        assertEquals(LocalDate.of(1984, 12, 14), film.getReleaseDate(),
                "Incorrect update film release date: " + film.getReleaseDate() + " but should be: " +
                        LocalDate.of(1984, 12, 14));
        assertEquals(110, film.getDuration(),
                "Incorrect update film duration: " + film.getDuration() + " but should be: " + 110);
        assertEquals(5L, film.getMpa().getId(),
                "Incorrect update film rating id: " + film.getMpa().getId() + " but should be: " + 5L);

        Film filmUp = filmStorage.getById(1L);
        assertNotNull(filmUp.getGenres());
        assertEquals(0, filmUp.getGenres().size(),
                "Incorrect size genres: " + filmUp.getGenres().size() + "but should be 0");
    }

    @Test
    void shouldUpdateRating() {
        Film upFilm = filmStorage.update(new Film(1L, "Harry Potter", "d".repeat(100),
                LocalDate.of(2002, 3, 21), 120, ratingStorage.getRatingById(2L)));
        assertEquals(1L, upFilm.getId(),
                "Incorrect create film id: " + upFilm.getId() + " but should be: " + 1L);
        assertEquals("Harry Potter", upFilm.getName(),
                "Incorrect create film name: " + upFilm.getName() + " but should be: " + "Harry Potter");
        assertEquals("d".repeat(100), upFilm.getDescription(), "Incorrect create film description: " +
                upFilm.getDescription() + " but should be: " + "d".repeat(100));
        assertEquals(LocalDate.of(2002, 3, 21), upFilm.getReleaseDate(),
                "Incorrect create film release date: " + upFilm.getReleaseDate() + " but should be: " +
                        LocalDate.of(2002, 3, 21));
        assertEquals(120, upFilm.getDuration(),
                "Incorrect create film duration: " + upFilm.getDuration() + " but should be: " + 120);
        assertEquals(2L, upFilm.getMpa().getId(),
                "Incorrect create film rating id: " + upFilm.getMpa().getId() + " but should be: " + 2L);
        assertEquals("PG", upFilm.getMpa().getName(),
                "Incorrect create film rating name: " + upFilm.getMpa().getName() + " but should be: " + "PG");

        Film getFilm = filmStorage.getById(1L);
        assertEquals(1L, getFilm.getId(),
                "Incorrect create film id: " + getFilm.getId() + " but should be: " + 1L);
        assertEquals("Harry Potter", getFilm.getName(),
                "Incorrect create film name: " + getFilm.getName() + " but should be: " + "Harry Potter");
        assertEquals("d".repeat(100), getFilm.getDescription(), "Incorrect create film description: " +
                getFilm.getDescription() + " but should be: " + "d".repeat(100));
        assertEquals(LocalDate.of(2002, 3, 21), getFilm.getReleaseDate(),
                "Incorrect create film release date: " + getFilm.getReleaseDate() + " but should be: " +
                        LocalDate.of(2002, 3, 21));
        assertEquals(120, getFilm.getDuration(),
                "Incorrect create film duration: " + getFilm.getDuration() + " but should be: " + 120);
        assertEquals(2L, getFilm.getMpa().getId(),
                "Incorrect create film rating id: " + getFilm.getMpa().getId() + " but should be: " + 2L);
        assertEquals("PG", getFilm.getMpa().getName(),
                "Incorrect create film rating name: " + getFilm.getMpa().getName() + " but should be: " + "PG");
    }

    @Test
    void shouldGetListFilms() {
        List<Film> films = filmStorage.getList();
        assertNotNull(films, "Get return empty film list");

        Film film = films.get(4);
        assertNotNull(film, "Get list return empty film");
        assertEquals(5L, film.getId(),
                "Incorrect return film id: " + film.getId() + " but should be: " + 5L);
        assertEquals("Barbi", film.getName(),
                "Incorrect return film name: " + film.getName() + " but should be: " + "Barbi");
        assertEquals("Pink comedy", film.getDescription(), "Incorrect update film description: " +
                film.getDescription() + " but should be: " + "Pink comedy");
        assertEquals(LocalDate.of(2023, 7, 9), film.getReleaseDate(),
                "Incorrect update film release date: " + film.getReleaseDate() + " but should be: " +
                        LocalDate.of(2023, 7, 9));
        assertEquals(115, film.getDuration(),
                "Incorrect update film duration: " + film.getDuration() + " but should be: " + 115);
        assertEquals(3L, film.getMpa().getId(),
                "Incorrect update film rating id: " + film.getMpa().getId() + " but should be: " + 3L);

        Set<Genre> genres = film.getGenres();
        assertEquals(2, genres.size());
    }

    @Test
    void shouldGetFilmById() {
        Film film = filmStorage.getById(2L);

        assertNotNull(film, "Get by id return empty film");
        assertEquals(2L, film.getId(),
                "Incorrect return film id: " + film.getId() + " but should be: " + 2L);
        assertEquals("La-la Land", film.getName(),
                "Incorrect return film name: " + film.getName() + " but should be: " + "La-la Land");
        assertEquals("Love story", film.getDescription(), "Incorrect return film description: " +
                film.getDescription() + " but should be: " + "Love story");
        assertEquals(LocalDate.of(2017, 1, 12), film.getReleaseDate(),
                "Incorrect return film release date: " + film.getReleaseDate() + " but should be: " +
                        LocalDate.of(2017, 1, 12));
        assertEquals(120, film.getDuration(),
                "Incorrect return film duration: " + film.getDuration() + " but should be: " + 120);
        assertEquals(4L, film.getMpa().getId(),
                "Incorrect return film rating id: " + film.getMpa().getId() + " but should be: " + 4L);
    }

    @Test
    void shouldUpdateGenresWithoutDuplicate() {
        Set<Genre> newSet = new HashSet<>();
        newSet.add(new Genre(2L, "Драма"));
        newSet.add(new Genre(3L, "Мультфильм"));
        newSet.add(new Genre(2L, "Драма"));
        Film upGenrFilm = filmStorage.update(new Film(1L, "Harry Potter", "d".repeat(100),
                LocalDate.of(2002, 3, 21), 120, ratingStorage.getRatingById(2L), newSet));
        assertEquals(1L, upGenrFilm.getId(),
                "Incorrect create film id: " + upGenrFilm.getId() + " but should be: " + 1L);
        assertEquals("Harry Potter", upGenrFilm.getName(),
                "Incorrect create film name: " + upGenrFilm.getName() + " but should be: " + "Harry Potter");
        assertEquals("d".repeat(100), upGenrFilm.getDescription(), "Incorrect create film description: " +
                upGenrFilm.getDescription() + " but should be: " + "d".repeat(100));
        assertEquals(LocalDate.of(2002, 3, 21), upGenrFilm.getReleaseDate(),
                "Incorrect create film release date: " + upGenrFilm.getReleaseDate() + " but should be: " +
                        LocalDate.of(2002, 3, 21));
        assertEquals(120, upGenrFilm.getDuration(),
                "Incorrect create film duration: " + upGenrFilm.getDuration() + " but should be: " + 120);
        assertEquals(2L, upGenrFilm.getMpa().getId(),
                "Incorrect create film rating id: " + upGenrFilm.getMpa().getId() + " but should be: " + 2L);
        assertEquals("PG", upGenrFilm.getMpa().getName(), "Incorrect create film rating name: " +
                upGenrFilm.getMpa().getName() + " but should be: " + "PG");
        assertEquals(2, newSet.size());

        Film filmUp = filmStorage.getById(1L);
        Set<Genre> testGenres = filmUp.getGenres();
        assertEquals(2, testGenres.size(),
                "Incorrect size genres list: " + testGenres.size() + " but should be: " + 2);
    }

    @Test
    void shouldUpdateGenres() {
        Set<Genre> newSet = new HashSet<>();
        newSet.add(new Genre(1L, null));
        newSet.add(new Genre(3L, null));
        newSet.add(new Genre(2L, null));
        Film upGenrFilm = filmStorage.update(new Film(1L, "Harry Potter", "d".repeat(100),
                LocalDate.of(2002, 3, 21), 120, ratingStorage.getRatingById(2L), newSet));
        assertEquals(1L, upGenrFilm.getId(),
                "Incorrect create film id: " + upGenrFilm.getId() + " but should be: " + 1L);
        assertEquals("Harry Potter", upGenrFilm.getName(),
                "Incorrect create film name: " + upGenrFilm.getName() + " but should be: " + "Harry Potter");
        assertEquals("d".repeat(100), upGenrFilm.getDescription(), "Incorrect create film description: " +
                upGenrFilm.getDescription() + " but should be: " + "d".repeat(100));
        assertEquals(LocalDate.of(2002, 3, 21), upGenrFilm.getReleaseDate(),
                "Incorrect create film release date: " + upGenrFilm.getReleaseDate() + " but should be: " +
                        LocalDate.of(2002, 3, 21));
        assertEquals(120, upGenrFilm.getDuration(),
                "Incorrect create film duration: " + upGenrFilm.getDuration() + " but should be: " + 120);
        assertEquals(2L, upGenrFilm.getMpa().getId(),
                "Incorrect create film rating id: " + upGenrFilm.getMpa().getId() + " but should be: " + 2L);
        assertEquals("PG", upGenrFilm.getMpa().getName(), "Incorrect create film rating name: " +
                upGenrFilm.getMpa().getName() + " but should be: " + "PG");
        assertEquals(3, newSet.size());

        Film film1 = filmStorage.getById(1L);
        Set<Genre> testGenres = film1.getGenres();
        assertEquals(3, testGenres.size());
    }

    @Test
    void shouldAddLikeToFilm() {
        filmStorage.addLike(2L, 1L);
        Like like = filmStorage.getLike(2L, 1L);

        assertNotNull(like, "Get by like return empty like");
        assertEquals(2L, like.getFilmId(),
                "Return film id from likes : " + like.getFilmId() + " but should be: " + 2L);
        assertEquals(1L, like.getUserId(),
                "Return user id from likes : " + like.getUserId() + " but should be: " + 1L);
    }

    @Test
    void shouldGetLike() {
        Like like = filmStorage.getLike(3L, 5L);

        assertNotNull(like, "Get by like return empty like");
        assertEquals(3L, like.getFilmId(),
                "Return film id from likes : " + like.getFilmId() + " but should be: " + 3L);
        assertEquals(5L, like.getUserId(),
                "Return user id from likes : " + like.getUserId() + " but should be: " + 5L);
    }

    @Test
    void shouldDeleteLikeToFilm() {
        Like like = filmStorage.getLike(4L, 1L);
        assertNotNull(like, "Get by like return empty like");

        filmStorage.deleteLike(4L, 1L);
        assertNull(filmStorage.getLike(4L, 1L), "Like not deleted");
    }

    @Test
    void shouldGetPopularFilms() {
        List<Film> popularFilms = filmStorage.getPopularFilms(3);
        assertEquals(3, popularFilms.size(),
                "Incorrect film list size: " + popularFilms.size() + " but should be: " + 3);

        Film popularFilm = popularFilms.get(0);
        assertEquals(1L, popularFilm.getId(), "Incorrect films in popular list");

        filmStorage.addLike(4L, 2L);
        filmStorage.addLike(4L, 3L);
        filmStorage.addLike(4L, 4L);
        filmStorage.addLike(4L, 5L);

        List<Film> newPopularFilms = filmStorage.getPopularFilms(1);
        assertEquals(1, newPopularFilms.size(),
                "Incorrect new film list size: " + newPopularFilms.size() + " but should be: " + 1);

        Film newPopularFilm = newPopularFilms.get(0);
        assertEquals(4L, newPopularFilm.getId(), "Incorrect films in popular list");
        assertEquals("Frozen", newPopularFilm.getName(),
                "Incorrect create film name: " + newPopularFilm.getName() + " but should be: " + "Frozen");
        assertEquals("Disney animation", newPopularFilm.getDescription(),
                "Incorrect create film description: " +
                        newPopularFilm.getDescription() + " but should be: " + "Disney animation");
        assertEquals(LocalDate.of(2013, 12, 12), newPopularFilm.getReleaseDate(),
                "Incorrect create film release date: " + newPopularFilm.getReleaseDate() + " but should be: " +
                        LocalDate.of(2013, 12, 12));
        assertEquals(100, newPopularFilm.getDuration(),
                "Incorrect create film duration: " + newPopularFilm.getDuration() + " but should be: " + 100);
        assertEquals(1L, newPopularFilm.getMpa().getId(), "Incorrect create film rating id: " +
                newPopularFilm.getMpa().getId() + " but should be: " + 1L);
        assertEquals("G", newPopularFilm.getMpa().getName(), "Incorrect create film rating id: " +
                newPopularFilm.getMpa().getName() + " but should be: " + "G");

        List<Film> allPopularFilms = filmStorage.getPopularFilms(10);
        assertEquals(5, allPopularFilms.size(),
                "Incorrect all film list size: " + allPopularFilms.size() + " but should be: " + 5);
    }

    @Test
    void shouldAddGenresToFilm() {
        List<Long> genreIds = filmStorage.getGenresIdByFilm(4L);

        assertEquals(1, genreIds.size(),
                "Incorrect genre ids list size: " + genreIds.size() + " but should be: " + 1);
        filmStorage.addGenreToFilm(4L, 1L);
        List<Long> newGenreIds = filmStorage.getGenresIdByFilm(4L);
        assertEquals(2, newGenreIds.size(),
                "Incorrect new genre ids list size: " + newGenreIds.size() + " but should be: " + 2);
        assertTrue(newGenreIds.contains(1L));
    }

    @Test
    void shouldGetGenresIdByFilm() {
        List<Long> genreIds = filmStorage.getGenresIdByFilm(2L);

        assertEquals(1, genreIds.size(),
                "Incorrect genre ids list size: " + genreIds.size() + " but should be: " + 1);
        assertEquals(2L, genreIds.get(0),
                "Incorrect return genre id from list: " + genreIds.get(0) + " but should be: " + 2L);
    }
}