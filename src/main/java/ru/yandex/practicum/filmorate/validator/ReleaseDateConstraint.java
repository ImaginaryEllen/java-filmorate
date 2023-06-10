package ru.yandex.practicum.filmorate.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ReleaseDateValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReleaseDateConstraint {
	String message() default "Release date cannot be earlier than 28.12.1895";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
