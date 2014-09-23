package org.test.commons.exception;

public class ApplicationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Exception exception;

	public ApplicationException(String message, Exception exception) {
		super(message);
		this.exception = exception;
	}

	public ApplicationException(String message) {
		this(message, null);
	}

	public ApplicationException(Exception exception) {
		this(null, exception);
	}

	public Exception getException() {
		return exception;
	}

	/**
	 * 始终得到�?��层的异常，最�?��抛出的异�?
	 * <p>
	 * DBException dbe = new DBException("DBexception测试错误"); DAOException daoe =
	 * new DAOException(dbe); daoe.getRootCause()=dbe
	 * 
	 * @return
	 */
	public Exception getRootCause() {
		if (exception instanceof ApplicationException) {
			return ((ApplicationException) exception).getRootCause();
		}
		return exception == null ? this : exception;
	}

	public String toString() {
		if (exception instanceof ApplicationException) {
			return ((ApplicationException) exception).toString();
		}
		return exception == null ? super.toString() : exception.toString();
	}
}
