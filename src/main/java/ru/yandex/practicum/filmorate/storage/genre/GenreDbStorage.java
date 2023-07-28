package ru.yandex.practicum.filmorate.storage.genre;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class GenreDbStorage implements GenreStorage {
    private final NamedParameterJdbcOperations jdbcOperations;

    public GenreDbStorage(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<Genre> getGenreList() {
        final String sqlQuery = "select * from genres ";
        return jdbcOperations.query(sqlQuery, new GenreRowMapper());
    }

    @Override
    public Genre getGenreById(Long id) {
        final String sqlQuery = "select * from genres where genre_id = :genre_id";
        final List<Genre> genres = jdbcOperations.query(sqlQuery, Map.of("genre_id", id), new GenreRowMapper());
        if (genres.size() != 1) {
            return null;
        }
        return genres.get(0);
    }

    private static class GenreRowMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Genre(rs.getLong("genre_id"),
                    rs.getString("name"));
        }
    }
}
