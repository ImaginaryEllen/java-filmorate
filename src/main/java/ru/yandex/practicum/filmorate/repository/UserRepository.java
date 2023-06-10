package ru.yandex.practicum.filmorate.repository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.UserException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
@Component
public class UserRepository {
	private long idNumber;
	private Map<Long, User> users = new HashMap<>();


	public User createUser(@Valid @RequestBody User user) {
		user.setId(++idNumber);
		if (user.getName() == null || user.getName().isBlank() || user.getName().isEmpty()) {
			user.setName(user.getLogin());
		}
		users.put(user.getId(), user);
		return user;
	}

	public User updateUser(@Valid @RequestBody User user) {
		if (!users.containsKey(user.getId())) {
			log.warn("User does not exist: {}", user);
			throw new UserException("Not found User with id : " + user.getId());
		}
		users.put(user.getId(), user);
		return user;
	}
}
