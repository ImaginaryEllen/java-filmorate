package ru.yandex.practicum.filmorate.service.rating;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.rating.RatingStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BaseRatingService implements RatingService {
    private final RatingStorage ratingStorage;

    @Override
    public List<Rating> getRatingList() {
        return ratingStorage.getRatingList();
    }

    @Override
    public Rating getRatingById(Long id) {
        Rating rating = ratingStorage.getRatingById(id);
        if (rating == null) {
            throw new NotFoundException("Rating with ID: " + id + " not found");
        }
        return rating;
    }
}
