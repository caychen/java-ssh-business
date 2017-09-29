package org.com.cay.exception;

public class SysException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String errorMessage;

	public SysException(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return this.errorMessage;
	}
}
