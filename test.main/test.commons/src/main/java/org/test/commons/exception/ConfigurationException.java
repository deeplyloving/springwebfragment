package org.test.commons.exception;

public class ConfigurationException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConfigurationException(String message) {
		super(message);
	}

	public ConfigurationException(String message, Exception Exception) {
		super(message, Exception);
	}

	public ConfigurationException(Exception Exception) {
		super(null, Exception);
	}
}
