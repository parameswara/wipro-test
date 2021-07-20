package com.wiprotest.parameswara.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MessageExceptionController {
	@ExceptionHandler(value = MessageNotfoundException.class)
	public ResponseEntity<Object> exception(MessageNotfoundException exception) {
		return new ResponseEntity<>("Message not found", HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = InvalidRequestException.class)
	public ResponseEntity<Object> exception(InvalidRequestException exception) {
		return new ResponseEntity<>("Invalid request : "+exception.getMessage(), HttpStatus.NOT_FOUND);
	}
	
}
