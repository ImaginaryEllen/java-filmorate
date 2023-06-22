package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
@Validated
@Slf4j
@RequiredArgsConstructor
public class UserController {
	private final UserService service;

	@GetMapping()
	public List<User> get() {
		return new ArrayList<>(service.getList());
	}

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public User post(@Valid @RequestBody User user) {
		log.info("Create user: {} - STARTED", user);
		user = service.create(user);
		log.info("Create user: {} - FINISHED", user);
		return user;
	}

	@PutMapping()
	public User put(@Valid @RequestBody User user) {
		log.info("Update user: {} - STARTED", user);
		user = service.update(user);
		log.info("Update user: {} - FINISHED", user);
		return user;
	}

	@GetMapping("/{id}")
	public User getById(@PathVariable long id) {
		log.info("Get user by ID: {}", id);
		return service.getById(id);
	}

	@PutMapping("/{id}/friends/{friendId}")
	public void putFriend(@PathVariable long id, @PathVariable long friendId) {
		log.info("Add friend to user by ID: {}", id);
		service.addFriend(id, friendId);
	}

	@DeleteMapping("/{id}/friends/{friendId}")
	public void deleteFriend(@PathVariable long id, @PathVariable long friendId) {
		log.info("Delete friend from user by ID: {}", id);
		service.deleteFriend(id, friendId);
	}

	@GetMapping("/{id}/friends")
	public List<User> getFriend(@PathVariable long id) {
		log.info("Get user friends by ID: {}", id);
		return service.getFriend(id);
	}

	@GetMapping("/{id}/friends/common/{otherId}")
	public List<User> getCommonFriend(@PathVariable long id, @PathVariable long otherId) {
		return service.getCommonFriendList(id, otherId);
	}
}
