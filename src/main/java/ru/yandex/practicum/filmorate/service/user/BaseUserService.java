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
		return storage.create(user);
	}

	@Override
	public User update(User user) {
		return storage.update(user);
	}

	@Override
	public List<User> getList() {
		return storage.getList();
	}

	@Override
	public void addFriend(long userId, long friendId) {
		if (userId == friendId) {
			throw new IncorrectArgumentException(
					"User ID: " + userId + "cannot equals friend ID: " + friendId);
		}
		User user = getById(userId);
		User friend = getById(friendId);
		storage.addFriend(user, friend);
	}

	@Override
	public void deleteFriend(long userId, long friendId) {
		if (userId == friendId) {
			throw new IncorrectArgumentException(
					"User ID: " + userId + "cannot equals friend ID: " + friendId);
		}
		User user = getById(userId);
		User friend = getById(friendId);
		storage.deleteFriend(user, friend);
	}

	@Override
	public List<User> getCommonFriendList(long userId, long friendId) {
		if (userId == friendId) {
			throw new IncorrectArgumentException(
					"User ID: " + userId + "cannot equals friend ID: " + friendId);
		}
		User user = getById(userId);
		User friend = getById(friendId);
		return storage.getCommonFriendList(user, friend);
	}

	@Override
	public List<User> getFriend(long id) {
		User user = getById(id);
		return storage.getFriend(user);
	}

	@Override
	public User getById(long id) {
		final User user = storage.getById(id);
		if (user == null) {
			throw new NotFoundException("User with id: " + id + " not found");
		}
		return user;
	}
}
