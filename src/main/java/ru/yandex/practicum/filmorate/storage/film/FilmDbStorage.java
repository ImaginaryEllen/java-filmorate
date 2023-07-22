package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.service.genre.BaseGenreService;
import ru.yandex.practicum.filmorate.service.genre.GenreService;
import ru.yandex.practicum.filmorate.service.rating.BaseRatingService;
import ru.yandex.practicum.filmorate.service.rating.RatingService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@Import({BaseRatingService.class, BaseGenreService.class})
public class FilmDbStorage implements FilmStorage {
    private final NamedParameterJdbcOperations jdbcOperations;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private GenreService genreService;

    public FilmDbStorage(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public Film create(Film film) {
        final String sqlQuery = "insert into films(name, description, release_date, duration, rating_id) " +
                "values (:name, :description, :release_date, :duration, :rating_id) ";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("name", film.getName());
        map.addValue("description", film.getDescription());
        map.addValue("release_date", film.getReleaseDate());
        map.addValue("duration", film.getDuration());
        map.addValue("rating_id", film.getMpa().getId());
        jdbcOperations.update(sqlQuery, map, keyHolder);
        film.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        saveGenre(film);
        film.setMpa(ratingService.getRatingById(film.getMpa().getId()));
        return film;
    }

    @Override
    public Film update(Film film) {
        final String sqlQuery = "update films set name = :name, description = :description, " +
                "release_date = :release_date, duration = :duration, rating_id = :rating_id " +
                "where film_id = :id ";
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("id", film.getId());
        map.addValue("name", film.getName());
        map.addValue("description", film.getDescription());
        map.addValue("release_date", film.getReleaseDate());
        map.addValue("duration", film.getDuration());
        map.addValue("rating_id", film.getMpa().getId());
        jdbcOperations.update(sqlQuery, map);
        film.setMpa(ratingService.getRatingById(film.getMpa().getId()));
        saveGenre(film);
        return film;
    }

    @Override
    public List<Film> getList() {
        final String sqlQuery = "select film_id, name, description, release_date, duration, rating_id " +
                "from films";
        final List<Film> films = jdbcOperations.query(sqlQuery, new FilmRowMapper());
        for (Film film : films) {
            film.setMpa(ratingService.getRatingById(film.getMpa().getId()));
            film.setGenres(getGenresByFilmId(film.getId()));
        }
        return films;
    }

    @Override
    public Film getById(Long id) {
        final String sqlQuery = "select film_id, name, description, release_date, duration, rating_id " +
                "from films " +
                "where film_id = :id ";
        final List<Film> films = jdbcOperations.query(sqlQuery, Map.of("id", id), new FilmRowMapper());
        if (films.size() != 1) {
            return null;
        }
        Film film = films.get(0);
        film.setMpa(ratingService.getRatingById(film.getMpa().getId()));
        film.setGenres(getGenresByFilmId(id));
        return film;
    }

    @Override
    public void addLike(Long filmId, Long userId) {
        final String sqlQuery = "insert into likes (film_id, user_id) " +
                "values (:film_id, :user_id) ";
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("film_id", filmId);
        map.addValue("user_id", userId);
        jdbcOperations.update(sqlQuery, map);
    }

    @Override
    public Like getLike(Long filmId, Long userId) {
        final String sqlQuery = "select * from likes where film_id = :film_id and user_id = :user_id";
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("film_id", filmId);
        map.addValue("user_id", userId);
        final List<Like> likes = jdbcOperations.query(sqlQuery, map, new LikeRowMapper());
        if (likes.size() != 1) {
            return null;
        }
        return likes.get(0);
    }

    @Override
    public void deleteLike(Long filmId, Long userId) {
        final String sqlQuery = "delete from likes where user_id = :user_id and film_id = :film_id ";
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("film_id", filmId);
        map.addValue("user_id", userId);
        jdbcOperations.update(sqlQuery, map);
    }

    @Override
    public List<Film> getPopularFilms(int count) {
        if (getList().size() < count) {
            count = getList().size();
        }
        final String sqlQuery = "select f.film_id, name, description, release_date, duration, rating_id " +
                "from films as f " +
                "left join likes as l on f.film_id = l.film_id " +
                "group by f.film_id " +
                "order by count(l.user_id) desc " +
                "limit :count ";
        final List<Film> films = jdbcOperations.query(sqlQuery, Map.of("count", count), new FilmRowMapper());
        for (Film film : films) {
            film.setMpa(ratingService.getRatingById(film.getMpa().getId()));
            film.setGenres(getGenresByFilmId(film.getId()));
        }
        return films;
    }

    @Override
    public void addGenreToFilm(Long filmId, Long genreId) {
        final String sqlQuery = "insert into film_genres (film_id, genre_id) " +
                "values (:film_id, :genre_id) ";
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("film_id", filmId);
        map.addValue("genre_id", genreId);
        jdbcOperations.update(sqlQuery, map);
    }

    @Override
    public List<Long> getGenresIdByFilm(Long id) {
        final String sqlQuery = "select genre_id from film_genres where film_id = :film_id ";
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("film_id", id);
        return jdbcOperations.queryForList(sqlQuery, map, Long.class);
    }

    private void saveGenre(Film film) {
        deleteGenreByFilm(film.getId());
        for (Genre genre : film.getGenres()) {
            genre.setName(genreService.getGenreById(genre.getId()).getName());
            addGenreToFilm(film.getId(), genre.getId());
            film.setGenres(getGenresByFilmId(film.getId()));
        }
    }

    private void deleteGenreByFilm(Long id) {
        final String sqlQuery = "delete from film_genres where film_id = :film_id ";
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("film_id", id);
        jdbcOperations.update(sqlQuery, map);
    }

    private Set<Genre> getGenresByFilmId(Long id) {
        Set<Genre> genres = new HashSet<>();
        List<Long> genresId = getGenresIdByFilm(id);
        if (genresId.size() > 0) {
            for (Long genreId : genresId) {
                genres.add(genreService.getGenreById(genreId));
            }
        }
        return genres;
    }

    public static class FilmRowMapper implements RowMapper<Film> {
        @Override
        public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Film(rs.getLong("film_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDate("release_date").toLocalDate(),
                    rs.getInt("duration"),
                    new Rating(rs.getLong("rating_id"), null)
            );
        }
    }

    private static class LikeRowMapper implements RowMapper<Like> {
        @Override
        public Like mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Like(rs.getLong("film_id"),
                    rs.getLong("user_id"));
        }
    }
}
