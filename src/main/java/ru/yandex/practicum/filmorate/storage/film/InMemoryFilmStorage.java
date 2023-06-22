package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage {
	protected long idNumber;
	protected Map<Long, Film> films = new HashMap<>();
	protected Map<Long, Set<Long>> likes = new HashMap<>();

	@Override
	public Film create(@Valid @RequestBody Film film) {
		film.setId(++idNumber);
		films.put(film.getId(), film);
		return film;
	}

	@Override
	public Film update(Film film) {
		if (!films.containsKey(film.getId())) {
			throw new NotFoundException("Not found film with id: " + film.getId());
		}
		films.put(film.getId(), film);
		return film;
	}

	@Override
	public List<Film> getList() {
		return new ArrayList<>(films.values());
	}

	@Override
	public Film getById(long id) {
		if (id <= 0) {
			throw new NotFoundException("Film with id: " + id + " not found");
		}
		return films.get(id);
	}

	@Override
	public void addLike(Film film, User user) {
		Set<Long> userLikes = likes.computeIfAbsent(film.getId(), id -> new HashSet<>());
		userLikes.add(user.getId());
	}

	@Override
	public void deleteLike(Film film, User user) {
		Set<Long> userLikes = likes.computeIfAbsent(film.getId(), id -> new HashSet<>());
		userLikes.remove(user.getId());
	}

	@Override
	public List<Film> getPopularFilms(int count) {
		if (likes.size() > 0) {
			SortedMap<Long, Long> sorted = new TreeMap<>(Comparator.reverseOrder());
			for (Long filmId : likes.keySet()) {
				long likeSize = likes.get(filmId).size();
				if (likeSize != 0) {
					sorted.put(likeSize, filmId);
				return sorted.values().stream()
						.map(id -> films.get(id)).collect(Collectors.toCollection(() -> new ArrayList<>(count)));
				}
			}
		}
		return new ArrayList<>(films.values());
	}
}