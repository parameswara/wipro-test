package com.wiprotest.parameswara.exception;

public class InvalidRequestException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public InvalidRequestException(Exception e) {
		super(e);
	}
}
