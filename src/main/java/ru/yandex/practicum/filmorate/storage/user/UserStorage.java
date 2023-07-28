package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {

    User create(User user);

    User update(User user);

    List<User> getList();

    Optional<User> getById(Long id);

    void addFriend(Long userId, Long friendId);

    void deleteFriend(Long userId, Long friendId);

    List<User> getCommonFriendList(Long userId, Long friendId);

    List<User> getFriends(Long id);
}
