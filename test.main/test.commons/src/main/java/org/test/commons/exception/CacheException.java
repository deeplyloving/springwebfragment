package org.test.commons.exception;

public class CacheException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CacheException(String message) {
		super(message);
	}

	public CacheException(String message, Exception Exception) {
		super(message, Exception);
	}

	public CacheException(Exception Exception) {
		super(null, Exception);
	}
}
