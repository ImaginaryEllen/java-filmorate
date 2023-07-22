package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.service.rating.RatingService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/mpa")
@Slf4j
@RequiredArgsConstructor
public class RatingController {
    private final RatingService service;

    @GetMapping()
    public List<Rating> get() {
        return new ArrayList<>(service.getRatingList());
    }

    @GetMapping("/{id}")
    public Rating getById(@PathVariable Long id) {
        log.info("Get rating by ID: {}", id);
        return service.getRatingById(id);
    }
}
