package ru.yandex.practicum.filmorate.storage.rating;

import ru.yandex.practicum.filmorate.model.Rating;

import java.util.List;
import java.util.Optional;

public interface RatingStorage {

    List<Rating> getRatingList();

    Optional<Rating> getRatingById(Long id);
}
