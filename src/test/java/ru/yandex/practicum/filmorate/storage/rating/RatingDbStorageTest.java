package ru.yandex.practicum.filmorate.storage.rating;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.Rating;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@JdbcTest
@Import(RatingDbStorage.class)
class RatingDbStorageTest {
    @Autowired
    private RatingStorage ratingStorage;

    @Test
    void shouldGetRatingList() {
        List<Rating> ratings = ratingStorage.getRatingList();
        assertNotNull(ratings, "Return empty ratings list");
        assertEquals(5, ratings.size(),
                "Incorrect ratings list size: " + ratings.size() + "but should be: " + 5);

        Rating rating = ratings.get(0);
        assertEquals(1L, rating.getId(),
                "Incorrect rating id: " + rating.getId() + "but should be: " + 1L);
        assertEquals("G", rating.getName(),
                "Incorrect rating name: " + rating.getName() + "but should be: " + "G");
    }

    @Test
    void shouldGetRatingById() {
        Rating rating = ratingStorage.getRatingById(2L);

        assertEquals(2L, rating.getId(),
                "Incorrect rating id: " + rating.getId() + "but should be: " + 2L);
        assertEquals("PG", rating.getName(),
                "Incorrect rating name: " + rating.getName() + "but should be: " + "PG");
    }

    @Test
    void shouldGetEmptyRatingByIncorrectId() {
        Rating rating = ratingStorage.getRatingById(200L);
        assertNull(rating, "Return not empty rating");
    }
}