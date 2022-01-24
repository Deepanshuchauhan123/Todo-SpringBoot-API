package io.spring.todoapi.annotation;

import static java.lang.annotation.ElementType.FIELD
import static java.lang.annotation.RetentionPolicy.RUNTIME

import java.lang.annotation.Documented;
import java.lang.annotation.Retention
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload

@Target(FIELD)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = PriorityValidator.class)
public @interface PriorityValidation {

	String message() default "This is not a valid Priority of the Task!"

	Class<?>[] groups() default[]

	Class<? extends Payload>[] payload() default []
}
