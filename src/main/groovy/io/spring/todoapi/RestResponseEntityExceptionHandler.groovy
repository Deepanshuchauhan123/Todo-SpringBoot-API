package io.spring.todoapi

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

import io.jsonwebtoken.ExpiredJwtException

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = ExpiredJwtException.class)
	protected ResponseEntity<Object> handleBusinessException(RuntimeException exception, WebRequest request) {
		return new ResponseEntity("JWT Expired", HttpStatus.BAD_REQUEST);
	}
}
