package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

	User create(User user);

	User update(User user);

	List<User> getList();

	User getById(long id);

	void addFriend(User user, User friend);

	void deleteFriend(User user, User friend);

	List<User> getCommonFriendList(User user, User friend);

	List<User> getFriend(User user);
}
