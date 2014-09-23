package org.test.commons.exception;

public class DAOException extends ApplicationException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DAOException(String message) {
		super(message);
	}

	public DAOException(String message, Exception Exception) {
		super(message, Exception);
	}

	public DAOException(Exception Exception) {
		super(null, Exception);
	}
}
