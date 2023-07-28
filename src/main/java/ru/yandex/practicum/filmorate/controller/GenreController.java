package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.genre.GenreService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/genres")
@Slf4j
@RequiredArgsConstructor
public class GenreController {
    private final GenreService service;

    @GetMapping()
    public List<Genre> get() {
        return new ArrayList<>(service.getGenreList());
    }

    @GetMapping("/{id}")
    public Genre getById(@PathVariable Long id) {
        log.info("Get genre by ID: {}", id);
        return service.getGenreById(id);
    }
}
