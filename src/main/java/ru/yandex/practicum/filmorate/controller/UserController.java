package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
@Validated
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class UserController {
	public UserRepository repository = new UserRepository();

	@GetMapping()
	public List<User> getUsers() {
		return new ArrayList<>(repository.getUsers().values());
	}

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public User createUser(@Valid @RequestBody User user) {
		log.info("Create user: {} - STARTED", user);
		user = repository.createUser(user);
		log.info("Create user: {} - FINISHED", user);
		return user;
	}

	@PutMapping()
	public User updateUser(@Valid @RequestBody User user) {
		log.info("Update user: {} - STARTED", user);
		user = repository.updateUser(user);
		log.info("Update user: {} - FINISHED", user);
		return user;
	}
}
