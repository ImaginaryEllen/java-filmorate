package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.IncorrectArgumentException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BaseUserService implements UserService {
    private final UserStorage storage;

    @Override
    public User create(User user) {
        User newUser = storage.create(user);
        validationUser(newUser.getId());
        return newUser;
    }

    @Override
    public User update(User user) {
        validationUser(user.getId());
        return storage.update(user);
    }

    @Override
    public List<User> getList() {
        return storage.getList();
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        validationFriends(userId, friendId);
        storage.addFriend(userId, friendId);
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) {
        validationFriends(userId, friendId);
        storage.deleteFriend(userId, friendId);
    }

    @Override
    public List<User> getCommonFriendList(Long userId, Long friendId) {
        validationFriends(userId, friendId);
        return storage.getCommonFriendList(userId, friendId);
    }

    @Override
    public List<User> getFriend(Long id) {
        return storage.getFriends(id);
    }

    @Override
    public User getById(Long id) {
        return storage.getById(id).orElseThrow(() -> new NotFoundException("User with ID: " + id + " not found"));
    }

    private void validationUser(Long id) {
        if (storage.getById(id).isEmpty() || id <= 0) {
            throw new NotFoundException("User with ID: " + id + " not found");
        }
    }

    private void validationFriends(Long userId, Long friendId) {
        if (storage.getById(userId).isEmpty() || userId <= 0) {
            throw new NotFoundException("User with ID: " + userId + " not found");
        }
        if (storage.getById(friendId).isEmpty() || friendId <= 0) {
            throw new NotFoundException("User with ID: " + friendId + " not found");
        }
        if (userId.equals(friendId)) {
            throw new IncorrectArgumentException(
                    "User ID: " + userId + "cannot equals friend ID: " + friendId);
        }
    }
}
