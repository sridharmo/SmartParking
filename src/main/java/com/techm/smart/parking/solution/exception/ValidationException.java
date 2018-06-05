package com.techm.smart.parking.solution.exception;

/**
 * It is a used defined exception class <class>ServiceException</class>. It would
 * throw from Service and Controller layer of scheduler.
 * 
 */
public class ValidationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default Constructor.
	 */
	public ValidationException() {
		super();
	}

	/**
	 * Constructor with exception message.
	 * 
	 * @param msg
	 */
	public ValidationException(String msg) {
		super(msg);
	}

	/**
	 * Constructor with cause <object>throwable</object> of exception.
	 * 
	 * @param throwable
	 */
	public ValidationException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * Constructor with Cause <object>throwable</object> and message of exception.
	 * @param msg
	 * @param throwable
	 */
	public ValidationException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

}
