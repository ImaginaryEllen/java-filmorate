package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.FilmRepository;

@RestController
@RequestMapping("/films")
@Validated
@Slf4j
public class FilmController extends Controller<Film> {

	public FilmController(FilmRepository repository) {
		super(repository);
	}
}
