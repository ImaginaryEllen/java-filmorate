package ru.yandex.practicum.filmorate.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ReleaseDateValidator implements ConstraintValidator<ReleaseDateConstraint, LocalDate> {
	@Override
	public boolean isValid(LocalDate value, ConstraintValidatorContext constraintValidatorContext) {
		if (value == null) {
			return true;
		}
		return value.isAfter(LocalDate.of(1895, 12, 28));
	}
}
