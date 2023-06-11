package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FilmTest {

	private static final Validator validator;

	static {
		try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
			validator = validatorFactory.usingContext().getValidator();
		}
	}

	@Test
	void validateCorrectName() {
		Film film = new Film();
		film.setName("Interstellar");
		film.setDuration(169);
		Set<ConstraintViolation<Film>> validates = validator.validate(film);
		assertEquals(0, validates.size(),
				"Film name validate is not correct: expected - 0, actual -" + validates.size());
	}

	@Test
	void validateNameIsBlank() {
		Film film = new Film();
		film.setName("");
		film.setDuration(169);
		Set<ConstraintViolation<Film>> validates = validator.validate(film);
		assertEquals(1, validates.size(),
				"Film name validate is not correct: expected - 1, actual -" + validates.size());
		validates.stream().map(ConstraintViolation::getMessage)
				.forEach(System.out::println);
	}

	@Test
	void validateCorrectDescription() {
		Film film = new Film();
		film.setName("Interstellar");
		film.setDuration(169);
		film.setDescription("d".repeat(100));
		Set<ConstraintViolation<Film>> validates = validator.validate(film);
		assertEquals(0, validates.size(),
				"Film description validate is not correct: expected - 0, actual -" + validates.size());
	}

	@Test
	void validateDescriptionIsMore200() {
		Film film = new Film();
		film.setName("Interstellar");
		film.setDuration(169);
		film.setDescription("d".repeat(201));
		Set<ConstraintViolation<Film>> validates = validator.validate(film);
		assertEquals(1, validates.size(),
				"Film description validate is not correct: expected - 1, actual -" + validates.size());
		validates.stream().map(ConstraintViolation::getMessage)
				.forEach(System.out::println);
	}

	@Test
	void validateCorrectReleaseDate() {
		Film film = new Film();
		film.setName("Interstellar");
		film.setDuration(169);
		film.setReleaseDate(LocalDate.of(2014, 10, 26));
		Set<ConstraintViolation<Film>> validates = validator.validate(film);
		assertEquals(0, validates.size(),
				"Film releaseDate validate is not correct: expected - 0, actual -" + validates.size());
	}

	@Test
	void validateReleaseDateIsEarly28December1895() {
		Film film = new Film();
		film.setName("Interstellar");
		film.setDuration(169);
		film.setReleaseDate(LocalDate.of(1895, 10, 26));
		Set<ConstraintViolation<Film>> validates = validator.validate(film);
		assertEquals(1, validates.size(),
				"Film releaseDate validate is not correct: expected - 1, actual -" + validates.size());
		validates.stream().map(ConstraintViolation::getMessage)
				.forEach(System.out::println);
	}

	@Test
	void validateCorrectDuration() {
		Film film = new Film();
		film.setName("Interstellar");
		film.setDuration(169);
		Set<ConstraintViolation<Film>> validates = validator.validate(film);
		assertEquals(0, validates.size(),
				"Film duration validate is not correct: expected - 0, actual -" + validates.size());
	}

	@Test
	void validateDurationIsNegative() {
		Film film = new Film();
		film.setName("Interstellar");
		film.setDuration(-169);
		Set<ConstraintViolation<Film>> validates = validator.validate(film);
		assertEquals(1, validates.size(),
				"Film duration validate is not correct: expected - 1, actual -" + validates.size());
		validates.stream().map(ConstraintViolation::getMessage)
				.forEach(System.out::println);
	}
}