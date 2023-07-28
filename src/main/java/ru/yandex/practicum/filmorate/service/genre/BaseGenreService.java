package ru.yandex.practicum.filmorate.service.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BaseGenreService implements GenreService {
    private final GenreStorage genreStorage;

    @Override
    public List<Genre> getGenreList() {
        return genreStorage.getGenreList();
    }

    @Override
    public Genre getGenreById(Long id) {
        Genre genre = genreStorage.getGenreById(id);
        if (genre == null) {
            throw new NotFoundException("Genre with ID: " + id + " not found");
        }
        return genre;
    }
}
