package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.Test;

import javax.validation.*;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
	private static final Validator validator;

	static {
		try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
			validator = validatorFactory.usingContext().getValidator();
		}
	}

	@Test
	void validateCorrectEmail() {
		User user = new User();
		user.setEmail("test@mail.com");
		user.setLogin("test");
		Set<ConstraintViolation<User>> validates = validator.validate(user);
		assertEquals(0, validates.size(),
				"User email validate is not correct: expected - 0, actual -" + validates.size());
	}

	@Test
	void validateEmailIsBlank() {
		User user = new User();
		user.setEmail(" ");
		user.setLogin("test");
		Set<ConstraintViolation<User>> validates = validator.validate(user);
		assertEquals(2, validates.size(),
				"User email validate is not correct: expected - 2, actual - " + validates.size());
		validates.stream().map(ConstraintViolation::getMessage)
				.forEach(System.out::println);
	}

	@Test
	void validateEmailIsNotCorrectFormat() {
		User user = new User();
		user.setEmail("test.mail.com");
		user.setLogin("test");
		Set<ConstraintViolation<User>> validates = validator.validate(user);
		assertEquals(1, validates.size(),
				"User email validate is not correct: expected - 1, actual - " + validates.size());
		validates.stream().map(ConstraintViolation::getMessage)
				.forEach(System.out::println);
	}

	@Test
	void validateCorrectLogin() {
		User user = new User();
		user.setLogin("test");
		user.setEmail("test@mail.com");
		Set<ConstraintViolation<User>> validates = validator.validate(user);
		assertEquals(0, validates.size(),
				"User login validate is not correct: expected - 0, actual -" + validates.size());
	}

	@Test
	void validateLoginIsBlank() {
		User user = new User();
		user.setLogin("");
		user.setEmail("test@mail.com");
		Set<ConstraintViolation<User>> validates = validator.validate(user);
		assertEquals(2, validates.size(),
				"User login validate is not correct: expected - 2, actual - " + validates.size());
		validates.stream().map(ConstraintViolation::getMessage)
				.forEach(System.out::println);
	}

	@Test
	void validateLoginIsNotCorrectFormat() {
		User user = new User();
		user.setLogin(" test ");
		user.setEmail("test@mail.com");
		Set<ConstraintViolation<User>> validates = validator.validate(user);
		assertEquals(1, validates.size(),
				"User login validate is not correct: expected - 1, actual - " + validates.size());
		validates.stream().map(ConstraintViolation::getMessage)
				.forEach(System.out::println);
	}

	@Test
	void validateCorrectIfNameIsBlank() {
		User user = new User();
		user.setName("");
		user.setLogin("test");
		user.setEmail("test@mail.com");
		Set<ConstraintViolation<User>> validates = validator.validate(user);
		assertEquals(0, validates.size(),
				"User name validate is not correct: expected - 0, actual -" + validates.size());
	}

	@Test
	void validateCorrectBirthday() {
		User user = new User();
		user.setBirthday(LocalDate.of(1990, 12, 10));
		user.setLogin("test");
		user.setEmail("test@mail.com");
		Set<ConstraintViolation<User>> validates = validator.validate(user);
		assertEquals(0, validates.size(),
				"User birthday validate is not correct: expected - 0, actual -" + validates.size());
	}

	@Test
	void validateBirthdayIsFuture() {
		User user = new User();
		user.setBirthday(LocalDate.of(2990, 12, 10));
		user.setLogin("test");
		user.setEmail("test@mail.com");
		Set<ConstraintViolation<User>> validates = validator.validate(user);
		assertEquals(1, validates.size(),
				"User birthday validate is not correct: expected - 1, actual -" + validates.size());
		validates.stream().map(ConstraintViolation::getMessage)
				.forEach(System.out::println);
	}
}