package com.decathlon.usecase.error;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.modelmapper.MappingException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.decathlon.usecase.error.custom.SmsServiceCommunicationException;
import com.decathlon.usecase.error.custom.TeamNotFoundException;
import com.decathlon.usecase.error.response.CustomErrorResponse;
import com.fasterxml.jackson.databind.JsonMappingException;



@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {


	@ExceptionHandler({EntityNotFoundException.class,MappingException.class,ConstraintViolationException.class,
		org.hibernate.exception.ConstraintViolationException.class, 
		JsonMappingException.class, IllegalArgumentException.class, DataIntegrityViolationException.class,
		TeamNotFoundException.class})
	public ResponseEntity<?> entityNotFound(Exception ex, WebRequest request) {

		CustomErrorResponse errors = new CustomErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setError(ExceptionUtils.getRootCauseMessage(ex));
		errors.setStatus(HttpStatus.BAD_REQUEST.value());

		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

	}
	
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<?> elementNotFound(Exception ex, WebRequest request) {

		CustomErrorResponse errors = new CustomErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setError(ExceptionUtils.getRootCauseMessage(ex));
		errors.setStatus(HttpStatus.NOT_FOUND.value());

		return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);

	}
	
	@ExceptionHandler(SmsServiceCommunicationException.class)
	public ResponseEntity<?> communicationFailed(Exception ex, WebRequest request) {

		CustomErrorResponse errors = new CustomErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setError(ExceptionUtils.getRootCauseMessage(ex));
		errors.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

		return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("status", status.value());

		// Get all errors
		List<String> errors = new ArrayList<>();
		
		errors.add( ex.getMostSpecificCause().getMessage());
		body.put("errors", errors);

		return new ResponseEntity<>(body, headers, status);
	}
	
	// error handle for @Valid
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("status", status.value());

		// Get all errors
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage()+" for field "+x.getField())
				.collect(Collectors.toList());

		body.put("errors", errors);

		return new ResponseEntity<>(body, headers, status);

	}

}
