package com.hoiio.sdk.exception;

public class InternalServerException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public InternalServerException() {
		super();
	}
	
	public InternalServerException(Throwable cause) {
		super(cause);
	}

	public InternalServerException(String message) {
		super(message);
	}

}
