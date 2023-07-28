package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
public class UserDbStorage implements UserStorage {

    private final NamedParameterJdbcOperations jdbcOperations;

    public UserDbStorage(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public User create(User user) {
        user.setName(updateName(user.getName(), user.getLogin()));
        final String sqlQuery = "insert into users(email, login, name, birthday) " +
                "values (:email, :login, :name, :birthday) ";
        MapSqlParameterSource map = createUserParam(user);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(sqlQuery, map, keyHolder);
        user.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return user;
    }

    @Override
    public User update(User user) {
        user.setName(updateName(user.getName(), user.getLogin()));
        final String sqlQuery = "update users set email = :email, login = :login, name =:name, birthday = :birthday " +
                "where user_id = :id ";
        MapSqlParameterSource map = createUserParam(user);
        jdbcOperations.update(sqlQuery, map);
        return user;
    }

    @Override
    public List<User> getList() {
        final String sqlQuery = "select user_id, email, login, name, birthday " +
                "from users ";
        return jdbcOperations.query(sqlQuery, new UserRowMapper());
    }

    @Override
    public Optional<User> getById(Long id) {
        final String sqlQuery = "select user_id, email, login, name, birthday " +
                "from users " +
                "where user_id = :id ";
        final List<User> users = jdbcOperations.query(sqlQuery, Map.of("id", id), new UserRowMapper());
        if (users.size() != 1) {
            return Optional.empty();
        }
        return Optional.of(users.get(0));
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        boolean status = checkFriendship(userId, friendId);
        if (status) {
            updateFriendshipStatus(friendId, userId, true);
        }
        final String sqlQuery = "insert into friends (user_id, friend_id, status)" +
                "values (:user_id, :friend_id, :status) ";
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("user_id", userId);
        map.addValue("friend_id", friendId);
        map.addValue("status", status);
        jdbcOperations.update(sqlQuery, map);
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) {
        boolean status = checkFriendship(userId, friendId);
        if (status) {
            updateFriendshipStatus(friendId, userId, false);
        }
        final String sqlQuery = "delete from friends where user_id = :user_id and friend_id = :friend_id ";
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("user_id", userId);
        map.addValue("friend_id", friendId);
        jdbcOperations.update(sqlQuery, map);
    }

    @Override
    public List<User> getCommonFriendList(Long userId, Long friendId) {
        final String sqlQuery = "select * " +
                "from users u " +
                "where u.user_id in " +
                "(select f1.friend_id " +
                "from friends as f1 " +
                "inner join friends as f2 on f1.friend_id = f2.friend_id " +
                "where f1.user_id = :user_id and f2.user_id = :friend_id) ";
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("user_id", userId);
        map.addValue("friend_id", friendId);
        return jdbcOperations.query(sqlQuery, map, new UserRowMapper());
    }

    @Override
    public List<User> getFriends(Long id) {
        final String sqlQuery = "select friend_id, email, login, name, birthday " +
                "from friends as f " +
                "inner join users as u on f.friend_id = u.user_id " +
                "where f.user_id =  :user_id";
        return jdbcOperations.query(sqlQuery, Map.of("user_id", id), new FriendRowMapper());
    }

    private MapSqlParameterSource createUserParam(User user) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        if (user.getId() != null) {
            map.addValue("id", user.getId());
        }
        map.addValue("email", user.getEmail());
        map.addValue("login", user.getLogin());
        map.addValue("name", user.getName());
        map.addValue("birthday", user.getBirthday());
        return map;
    }

    private boolean checkFriendship(Long userId, Long friendId) {
        return findFriendship(userId, friendId).size() == findFriendship(friendId, userId).size();
    }

    private List<User> findFriendship(Long userId, Long friendId) {
        final String sqlQuery = "select friend_id, email, login, name, birthday " +
                "from friends as f inner join users as u on f.user_id = u.user_id " +
                "where f.user_id = :user_id and f.friend_id = :friend_id ";
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("user_id", userId);
        map.addValue("friend_id", friendId);
        return jdbcOperations.query(sqlQuery, map, new FriendRowMapper());
    }

    private String updateName(String name, String login) {
        if (name == null || name.isBlank() || name.isEmpty()) {
            return login;
        }
        return name;
    }

    private void updateFriendshipStatus(Long userId, Long friendId, boolean status) {
        if (checkFriendshipInTable(userId, friendId, status)) {
            final String sqlQuery = "update friends set user_id = :user_id, friend_id = :friend_id, status = :status " +
                    "where user_id = :user_id and friend_id = :friend_id ";
            MapSqlParameterSource map = new MapSqlParameterSource();
            map.addValue("user_id", userId);
            map.addValue("friend_id", friendId);
            map.addValue("status", status);
            jdbcOperations.update(sqlQuery, map);
        }
    }

    private boolean checkFriendshipInTable(Long userId, Long friendId, boolean status) {
        final String sqlQuery = "select friend_id, email, login, name, birthday from friends as f " +
                "inner join users u on u.user_id = f.friend_id " +
                "where f.user_id = :user_id and f.friend_id = :friend_id and status = :status ";
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("user_id", userId);
        map.addValue("friend_id", friendId);
        map.addValue("status", status);
        return jdbcOperations.query(sqlQuery, map, new FriendRowMapper()).size() == 1;
    }

    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(rs.getLong("user_id"),
                    rs.getString("email"),
                    rs.getString("login"),
                    rs.getString("name"),
                    rs.getDate("birthday").toLocalDate()
            );
        }
    }

    private static class FriendRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(rs.getLong("friend_id"),
                    rs.getString("email"),
                    rs.getString("login"),
                    rs.getString("name"),
                    rs.getDate("birthday").toLocalDate()
            );
        }
    }
}
