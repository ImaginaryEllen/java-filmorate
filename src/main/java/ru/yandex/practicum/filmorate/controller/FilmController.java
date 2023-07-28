package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/films")
@Validated
@Slf4j
@RequiredArgsConstructor
public class FilmController {
    private final FilmService service;

    @GetMapping()
    public List<Film> get() {
        return new ArrayList<>(service.getList());
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Film post(@Valid @RequestBody Film film) {
        log.info("Create film: {} - STARTED", film);
        film = service.create(film);
        log.info("Create film: {} - FINISHED", film);
        return film;
    }

    @PutMapping()
    public Film put(@Valid @RequestBody Film film) {
        log.info("Update film: {} - STARTED", film);
        film = service.update(film);
        log.info("Update film: {} - FINISHED", film);
        return film;
    }

    @GetMapping("/{id}")
    public Film getById(@PathVariable Long id) {
        log.info("Get film by ID: {}", id);
        return service.getById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void putLike(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Add like to film by ID: {}", id);
        service.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Delete like from film by ID: {}", id);
        service.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10") int count) {
        log.info("Get top films: {}", count);
        return service.getPopularFilms(count);
    }

    @PutMapping("/{id}/genre/{genreId}")
    public void putGenre(@PathVariable Long id, @PathVariable Long genreId) {
        log.info("Add genre to film by ID: {}", id);
        service.addGenresToFilm(id, genreId);
    }
}
