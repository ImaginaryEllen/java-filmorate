package ru.yandex.practicum.filmorate.storage.rating;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Rating;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class RatingDbStorage implements RatingStorage {
    private final NamedParameterJdbcOperations jdbcOperations;

    public RatingDbStorage(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<Rating> getRatingList() {
        final String sqlQuery = "select * from ratings ";
        return jdbcOperations.query(sqlQuery, new RatingRowMapper());
    }

    @Override
    public Optional<Rating> getRatingById(Long id) {
        final String sqlQuery = "select * from ratings where rating_id = :rating_id ";
        final List<Rating> ratings = jdbcOperations.query(sqlQuery, Map.of("rating_id", id), new RatingRowMapper());
        if (ratings.size() != 1) {
            return Optional.empty();
        }
        return Optional.of(ratings.get(0));
    }

    private static class RatingRowMapper implements RowMapper<Rating> {
        @Override
        public Rating mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Rating(rs.getLong("rating_id"),
                    rs.getString("name"));
        }
    }
}
