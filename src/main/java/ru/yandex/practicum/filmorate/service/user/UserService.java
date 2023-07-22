package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserService {

    User create(User user);

    User update(User user);

    List<User> getList();

    void addFriend(Long userId, Long friendId);

    void deleteFriend(Long userId, Long friendId);

    List<User> getCommonFriendList(Long userId, Long friendId);

    List<User> getFriend(Long id);

    User getById(Long id);
}
