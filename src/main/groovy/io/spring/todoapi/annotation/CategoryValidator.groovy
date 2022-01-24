package io.spring.todoapi.annotation

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

import net.bytebuddy.implementation.Implementation

class CategoryValidator implements ConstraintValidator<CategoryValidation,String>{

	def categories = [
		"New",
		"InProgress",
		"Completed"
	]
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		return categories.contains(value)
	}
}
