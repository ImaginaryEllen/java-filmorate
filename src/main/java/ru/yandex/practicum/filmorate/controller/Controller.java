package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Entity;
import ru.yandex.practicum.filmorate.repository.Repository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public abstract class Controller<T extends Entity> {
	protected Repository<T> repository;

	@GetMapping()
	public List<T> get() {
		return new ArrayList<>(repository.getList());
	}

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public T post(@Valid @RequestBody T entity) {
		log.info("Create: {} - STARTED", entity);
		entity = repository.create(entity);
		log.info("Create: {} - FINISHED", entity);
		return entity;
	}

	@PutMapping()
	public T put(@Valid @RequestBody T entity) {
		log.info("Update: {} - STARTED", entity);
		entity = repository.update(entity);
		log.info("Update: {} - FINISHED", entity);
		return entity;
	}
}
