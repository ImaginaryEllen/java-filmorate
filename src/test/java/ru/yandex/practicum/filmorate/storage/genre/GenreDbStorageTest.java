package ru.yandex.practicum.filmorate.storage.genre;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(GenreDbStorage.class)
class GenreDbStorageTest {
    @Autowired
    private GenreStorage genreStorage;

    @Test
    void shouldGetGenreList() {
        List<Genre> genres = genreStorage.getGenreList();
        assertNotNull(genres, "Return empty genres list");
        assertEquals(6, genres.size(),
                "Incorrect genres list size: " + genres.size() + "but should be: " + 6);

        Genre genre = genres.get(0);
        assertNotNull(genre, "Return empty genre");
        assertEquals(1L, genre.getId(),
                "Incorrect genre id: " + genre.getId() + "but should be: " + 1L);
        assertEquals("Комедия", genre.getName(),
                "Incorrect genre name: " + genre.getName() + "but should be: " + "Комедия");
    }

    @Test
    void shouldGetGenreById() {
        Genre genre = genreStorage.getGenreById(4L);

        assertNotNull(genre, "Return empty genre");
        assertEquals(4L, genre.getId(),
                "Incorrect genre id: " + genre.getId() + "but should be: " + 4L);
        assertEquals("Триллер", genre.getName(),
                "Incorrect genre name: " + genre.getName() + "but should be: " + "Триллер");
    }

    @Test
    void shouldGetEmptyGenreByIncorrectId() {
        Genre genre = genreStorage.getGenreById(200L);
        assertNull(genre, "Return not empty genre");
    }
}