package com.bookstore.exception;

import java.util.Date;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(NoDataFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ErrorMessage resourceNotFoundException(NoDataFoundException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND.value(), new Date(), ex.getMessage(),
				request.getDescription(false));
		log.error("NoDataFoundException occurred", ex);
		return message;
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorMessage sqlExceptionHandler(DataIntegrityViolationException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(),
				ex.getCause().getCause().getMessage(),request.getDescription(false));
		log.error("DataIntegrityViolationException occurred", ex);
		return message;
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorMessage globalExceptionHandler(Exception ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(),
				ex.getMessage(),request.getDescription(false));
		log.error("NoDataFoundException occurred", ex);
		return message;
	}
	
    @ExceptionHandler(BookAppException.class)
    public ErrorMessage handleBookAppException(BookAppException ex, WebRequest request) {
    	ErrorMessage message = new ErrorMessage(ex.getHttpStatus().value(), new Date(),
    			ex.getResponse().toString(),request.getDescription(false));
        log.error("BookAppException occurred", ex);
        return message;
    }
}
