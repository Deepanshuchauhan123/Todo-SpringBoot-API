package io.spring.todoapi.annotation

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class PriorityValidator implements ConstraintValidator<PriorityValidation,String>{

	def priorities = ["High", "Medium", "Low"];

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		return priorities.contains(value);
	}
}
