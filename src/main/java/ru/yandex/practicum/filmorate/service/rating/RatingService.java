package ru.yandex.practicum.filmorate.service.rating;

import ru.yandex.practicum.filmorate.model.Rating;

import java.util.List;

public interface RatingService {

    List<Rating> getRatingList();

    Rating getRatingById(Long id);
}
