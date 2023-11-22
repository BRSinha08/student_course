
package com.assignment.student_course.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.UUID;

@RestControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobleExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<MyErrorDetails> otherExceptionHandler(MethodArgumentNotValidException manve) {

		MyErrorDetails error = new MyErrorDetails();
		error.setTimestamp(LocalDateTime.now());
		error.setMessage("Validation failed...");
		error.setDetails(manve.getBindingResult().getFieldError().getDefaultMessage());

		return new ResponseEntity<MyErrorDetails>(error, HttpStatus.NOT_ACCEPTABLE);

	}
	
	@ExceptionHandler(UserException.class)
	public ResponseEntity<MyErrorDetails> otherExceptionHandler(UserException ue, WebRequest wReq) {

		MyErrorDetails error = new MyErrorDetails();
		error.setTimestamp(LocalDateTime.now());
		error.setMessage(ue.getMessage());
		error.setDetails(wReq.getDescription(false));

		return new ResponseEntity<MyErrorDetails>(error, HttpStatus.BAD_REQUEST);

	}
	
	@ExceptionHandler(StudentException.class)
	public ResponseEntity<MyErrorDetails> otherExceptionHandler(StudentException pe, WebRequest wReq) {

		MyErrorDetails error = new MyErrorDetails();
		error.setTimestamp(LocalDateTime.now());
		error.setMessage(pe.getMessage());
		error.setDetails(wReq.getDescription(false));

		return new ResponseEntity<MyErrorDetails>(error, HttpStatus.BAD_REQUEST);

	}
	
	@ExceptionHandler(CourseException.class)
	public ResponseEntity<MyErrorDetails> otherExceptionHandler(CourseException ce, WebRequest wReq) {

		MyErrorDetails error = new MyErrorDetails();
		error.setTimestamp(LocalDateTime.now());
		error.setMessage(ce.getMessage());
		error.setDetails(wReq.getDescription(false));

		return new ResponseEntity<MyErrorDetails>(error, HttpStatus.BAD_REQUEST);

	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<MyErrorDetails> otherExceptionHandler(Exception e, WebRequest wReq) {

		MyErrorDetails error = new MyErrorDetails();
		error.setTimestamp(LocalDateTime.now());
		error.setMessage(e.getMessage());
		error.setDetails(wReq.getDescription(false));

		return new ResponseEntity<MyErrorDetails>(error, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(ApplicationException.class)
	public ResponseEntity<?> handleApplicationException( final ApplicationException exception,
														 final HttpServletRequest request) {

		var guid = UUID.randomUUID().toString();
		/*log.error(
				String.format("Error GUID=%s; error message: %s", guid, exception.getMessage()),
				exception
		);*/
		var response = new ApiErrorResponse(
				guid,
				exception.getErrorCode(),
				exception.getMessage(),
				exception.getHttpStatus().value(),
				exception.getHttpStatus().name(),
				request.getRequestURI(),
				request.getMethod(),
				LocalDateTime.now()
		);
		return new ResponseEntity<>(response, exception.getHttpStatus());
	}
	
	
}
