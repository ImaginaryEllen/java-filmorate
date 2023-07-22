package ru.yandex.practicum.filmorate.storage.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@JdbcTest
@Import(UserDbStorage.class)
class UserDbStorageTest {

    @Autowired
    private UserStorage userStorage;
    @Test
    void shouldCreateUser() {
        User user = userStorage.create(new User("anna@mail.com", "fanny", "Anna",
                LocalDate.of(2000, 2, 20)));

        assertNotNull(user, "Create return empty user");
        assertEquals(6L, user.getId(),
                "Incorrect create user id: " + user.getId() + " but should be: " + 6L);
        assertEquals("anna@mail.com", user.getEmail(),
                "Incorrect create user email: " + user.getEmail() + " but should be: " + "anna@mail.com");
        assertEquals("fanny", user.getLogin(),
                "Incorrect create user name: " + user.getLogin() + " but should be: " + "fanny");
        assertEquals("Anna", user.getName(),
                "Incorrect create user name: " + user.getName() + " but should be: " + "Anna");
        assertEquals(LocalDate.of(2000, 2, 20), user.getBirthday(),
                "Incorrect create user birthday: " + user.getBirthday() + " but should be: " +
                        LocalDate.of(2000, 2, 20));
    }

    @Test
    void shouldUpdateUser() {
        User user = userStorage.update(new User(5L, "hhh@mail.com", "harry", "Harold",
                LocalDate.of(1985, 2, 2)));

        assertNotNull(user, "Update return empty user");
        assertEquals(5L, user.getId(),
                "Incorrect update user id: " + user.getId() + " but should be: " + 5L);
        assertEquals("hhh@mail.com", user.getEmail(),
                "Incorrect update user email: " + user.getEmail() + " but should be: " + "hhh@mail.com");
        assertEquals("harry", user.getLogin(),
                "Incorrect update user name: " + user.getLogin() + " but should be: " + "harry");
        assertEquals("Harold", user.getName(),
                "Incorrect update user name: " + user.getName() + " but should be: " + "Harold");
        assertEquals(LocalDate.of(1985, 2, 2), user.getBirthday(),
                "Incorrect update user birthday: " + user.getBirthday() + " but should be: " +
                        LocalDate.of(1985, 2, 2));
    }

    @Test
    void shouldGetUsersList() {
        List<User> users = userStorage.getList();
        assertNotNull(users, "Get return empty user list");

        User user = users.get(1);
        assertNotNull(user, "Get list return empty user");
        assertEquals(2L, user.getId(),
                "Incorrect return user id: " + user.getId() + " but should be: " + 2L);
        assertEquals("bob@mail.com", user.getEmail(),
                "Incorrect return user email: " + user.getEmail() + " but should be: " + "bob@mail.com");
        assertEquals("bobby", user.getLogin(),
                "Incorrect return user name: " + user.getLogin() + " but should be: " + "bobby");
        assertEquals("Robert", user.getName(),
                "Incorrect return user name: " + user.getName() + " but should be: " + "Robert");
        assertEquals(LocalDate.of(1990, 1, 1), user.getBirthday(),
                "Incorrect return user birthday: " + user.getBirthday() + " but should be: " +
                        LocalDate.of(1990, 1, 1));
    }

    @Test
    void shouldGetUserById() {
        User user = userStorage.getById(1L);

        assertNotNull(user, "Get list return empty user");
        assertEquals(1L, user.getId(),
                "Incorrect return user id: " + user.getId() + " but should be: " + 1L);
        assertEquals("tess@mail.com", user.getEmail(),
                "Incorrect return user email: " + user.getEmail() + " but should be: " + "tess@mail.com");
        assertEquals("testy", user.getLogin(),
                "Incorrect return user name: " + user.getLogin() + " but should be: " + "testy");
        assertEquals("Tess", user.getName(),
                "Incorrect return user name: " + user.getName() + " but should be: " + "Tess");
        assertEquals(LocalDate.of(2000, 10, 10), user.getBirthday(),
                "Incorrect return user birthday: " + user.getBirthday() + " but should be: " +
                        LocalDate.of(2000, 10, 10));
    }

    @Test
    void shouldAddFriendToUser() {
        userStorage.addFriend(5L,1L);

        List<User> friends = userStorage.getFriends(5L);
        assertNotNull(friends, "Return empty friends list");

        User friend = friends.get(0);
        assertNotNull(friend, "Return empty friend from list");
        assertEquals(1L, friend.getId(),
                "Incorrect return friend id: " + friend.getId() + " but should be: " + 1L);
        assertEquals("tess@mail.com", friend.getEmail(),
                "Incorrect return friend email: " + friend.getEmail() + " but should be: " + "tess@mail.com");
        assertEquals("testy", friend.getLogin(),
                "Incorrect return friend name: " + friend.getLogin() + " but should be: " + "testy");
        assertEquals("Tess", friend.getName(),
                "Incorrect return friend name: " + friend.getName() + " but should be: " + "Tess");
        assertEquals(LocalDate.of(2000, 10, 10), friend.getBirthday(),
                "Incorrect return friend birthday: " + friend.getBirthday() + " but should be: " +
                        LocalDate.of(2000, 10, 10));
    }

    @Test
    void shouldDeleteFriend() {
        List<User> friends = userStorage.getFriends(2L);
        assertEquals(2, friends.size(), "Return incorrect friends list");

        userStorage.deleteFriend(2L, 3L);

        List<User> newFriends = userStorage.getFriends(2L);
        assertEquals(1, newFriends.size(), "Incorrect delete friend");
    }

    @Test
    void shouldGetCommonFriendList() {
        List<User> commonFriends = userStorage.getCommonFriendList(1L, 3L);

        assertNotNull(commonFriends, "Return empty common friends list");
        assertEquals(1, commonFriends.size(), "Incorrect friends list size");
    }

    @Test
    void shouldGetFriends() {
        List<User> friends = userStorage.getFriends(4L);

        assertNotNull(friends, "Return empty friends list");
        assertEquals(1, friends.size(), "Incorrect friends list size");
    }
}