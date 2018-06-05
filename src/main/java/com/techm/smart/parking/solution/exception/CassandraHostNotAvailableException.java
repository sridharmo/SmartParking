package com.techm.smart.parking.solution.exception;

public class CassandraHostNotAvailableException extends Exception {

	private static final long serialVersionUID = 1L;
	//private String errorMessage;
	
	public CassandraHostNotAvailableException(String newErrorMessage) {
		super(newErrorMessage);
		//errorMessage = newErrorMessage;
	}
	
	public CassandraHostNotAvailableException() {
		super();
	}
	
	public CassandraHostNotAvailableException(String newErrorMessage,Throwable cause) {
		super(newErrorMessage, cause);
	}
	

}
