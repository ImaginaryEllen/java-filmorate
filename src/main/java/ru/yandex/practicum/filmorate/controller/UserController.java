package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserRepository;

@RestController
@RequestMapping("/users")
@Validated
@Slf4j
public class UserController extends Controller<User> {

	public UserController(UserRepository repository) {
		super(repository);
	}
}
