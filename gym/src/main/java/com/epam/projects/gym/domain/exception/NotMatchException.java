package com.epam.projects.gym.domain.exception;

public class NotMatchException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotMatchException(String message) {
        super(message);
    }

    public NotMatchException(String message, Throwable cause) {
        super(message, cause);
    }	
	
}
