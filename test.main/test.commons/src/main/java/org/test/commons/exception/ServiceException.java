package org.test.commons.exception;

public class ServiceException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(String message, Exception Exception) {
		super(message, Exception);
	}

	public ServiceException(Exception Exception) {
		super(null, Exception);
	}
}
