package ru.yandex.practicum.filmorate.repository;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;

@NoArgsConstructor
@Component
public class UserRepository extends Repository<User> {

	@Override
	public User create(@Valid @RequestBody User user) {
		user.setId(++idNumber);
		if (user.getName() == null || user.getName().isBlank() || user.getName().isEmpty()) {
			user.setName(user.getLogin());
		}
		all.put(user.getId(), user);
		return user;
	}
}
