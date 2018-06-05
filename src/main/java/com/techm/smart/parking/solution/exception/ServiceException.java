package com.techm.smart.parking.solution.exception;

/**
 * It is a used defined exception class <class>ServiceException</class>. It would
 * throw from Service and Controller layer of scheduler.
 *
 */
public class ServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default Constructor.
	 */
	public ServiceException() {
		super();
	}

	/**
	 * Constructor with exception message.
	 * 
	 * @param msg
	 */
	public ServiceException(String msg) {
		super(msg);
	}

	/**
	 * Constructor with cause <object>throwable</object> of exception.
	 * 
	 * @param throwable
	 */
	public ServiceException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * Constructor with Cause <object>throwable</object> and message of exception.
	 * @param msg
	 * @param throwable
	 */
	public ServiceException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

}
