package com.techm.smart.parking.solution.exception;

public class ResourceNotFound extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ResourceNotFound(String newErrorMessage) {
		super(newErrorMessage);
	}
	
	public ResourceNotFound() {
		super();
	}
	
	public ResourceNotFound(String newErrorMessage,Throwable cause) {
		super(newErrorMessage, cause);
	}

}
