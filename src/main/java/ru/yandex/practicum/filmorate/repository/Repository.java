package ru.yandex.practicum.filmorate.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Entity;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class Repository<T extends Entity> {
	protected long idNumber;
	protected Map<Long, T> all = new HashMap<>();

	public abstract T create(@Valid @RequestBody T entity);

	public T update(@Valid @RequestBody T entity) {
		if (!all.containsKey(entity.getId())) {
			log.warn("Data does not exist: {}", entity);
			throw new NotFoundException("Not found data with id: " + entity.getId());
		}
		all.put(entity.getId(), entity);
		return entity;
	}

	public List<T> getList() {
		return new ArrayList<>(all.values());
	}
}
