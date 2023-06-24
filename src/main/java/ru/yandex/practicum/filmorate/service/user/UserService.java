package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserService {

	User create(User user);

	User update(User user);

	List<User> getList();

	void addFriend(long userId, long friendId);

	void deleteFriend(long userId, long friendId);

	List<User> getCommonFriendList(long userId, long friendId);

	List<User> getFriend(long id);

	User getById(long id);
}
