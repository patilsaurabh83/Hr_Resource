package com.cg.hrresource.exception;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cg.hrresource.dto.ErrorResp;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	// constraint violation handler

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<?> handleCustomException(CustomException ex) {
		ErrorResp errRes = new ErrorResp(LocalDateTime.now(), ex.getMessage());
		return new ResponseEntity<>(errRes, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> handleConstraintViolation(ConstraintViolationException ex) {
		ErrorResp errRes = new ErrorResp("Validation Failed...");
		return new ResponseEntity<>(errRes, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		List<String> validationErrs = new ArrayList<>();
		for (FieldError err : ex.getBindingResult().getFieldErrors())
			validationErrs.add(err.getDefaultMessage());

		ErrorResp errResp = new ErrorResp("Validation Failed...");
		return new ResponseEntity<Object>(errResp, status);
	}

	// NotEmpty field validation handler
	@ExceptionHandler(UnexpectedTypeException.class)
	public ResponseEntity<?> handleUnexpectedTypeException(UnexpectedTypeException ex) {
		ErrorResp errorResponse = new ErrorResp("Valiation Failed...");

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<?> handleConstraintViolationException(SQLIntegrityConstraintViolationException ex) {
		ErrorResp errorResponse = new ErrorResp("Valiation Failed...");
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	// Other exception handlers for different exceptions can be added here

	@ExceptionHandler(InvalidEmployeeException.class)

	public ResponseEntity<?> handleCustomException(InvalidEmployeeException ex) {

		ErrorResp errRes = new ErrorResp(null, ex.getMessage());

		return new ResponseEntity<>(errRes, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception ex) {
		ErrorResp errorResponse = new ErrorResp(LocalDateTime.now(), ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}