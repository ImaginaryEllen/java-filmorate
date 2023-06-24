package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryUserStorage implements UserStorage {
	protected long idNumber;
	protected Map<Long, User> users = new HashMap<>();
	protected Map<Long, Set<Long>> friends = new HashMap<>();

	@Override
	public User create(@Valid @RequestBody User user) {
		user.setId(++idNumber);
		if (user.getName() == null || user.getName().isBlank() || user.getName().isEmpty()) {
			user.setName(user.getLogin());
		}
		users.put(user.getId(), user);
		return user;
	}

	@Override
	public User update(@Valid @RequestBody User user) {
		if (!users.containsKey(user.getId())) {
			throw new NotFoundException("Not found user with id: " + user.getId());
		}
		users.put(user.getId(), user);
		return user;
	}

	@Override
	public List<User> getList() {
		return new ArrayList<>(users.values());
	}

	@Override
	public User getById(long id) {
		if (id <= 0) {
			throw new NotFoundException("User with id: " + id + " not found");
		}
		return users.get(id);
	}

	@Override
	public void addFriend(User user, User friend) {
		Set<Long> userFriends = friends.computeIfAbsent(user.getId(), id -> new HashSet<>());
		userFriends.add(friend.getId());

		Set<Long> friendFriends = friends.computeIfAbsent(friend.getId(), id -> new HashSet<>());
		friendFriends.add(user.getId());
	}

	@Override
	public void deleteFriend(User user, User friend) {
		Set<Long> userFriends = friends.computeIfAbsent(user.getId(), id -> new HashSet<>());
		userFriends.remove(friend.getId());

		Set<Long> friendFriends = friends.computeIfAbsent(friend.getId(), id -> new HashSet<>());
		friendFriends.remove(user.getId());
	}

	@Override
	public List<User> getCommonFriendList(User user, User friend) {
		List<User> list = new ArrayList<>();
		if (friends.size() > 0) {
			Set<Long> userFriends = friends.get(user.getId());
			Set<Long> friendFriends = friends.get(friend.getId());
			list = userFriends.stream()
					.filter(friendFriends::contains).map(id -> users.get(id)).collect(Collectors.toList());
		}
		return list;
	}

	@Override
	public List<User> getFriend(User user) {
		return friends.get(user.getId()).stream().map(id -> users.get(id)).collect(Collectors.toList());
	}
}
