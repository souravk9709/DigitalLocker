package com.dfrozensoft.locker.exception;

public class UnknownException extends RuntimeException {
	/**
	 *
	 */
	private static final long serialVersionUID = 5661779395098074988L;

	public UnknownException() {
	}

	public UnknownException(String message) {
		super(message);
	}
}
