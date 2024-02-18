package com.epam.projects.gym.domain.exception;

public class CreationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public CreationException(String message) {
        super(message);
    }

    public CreationException(String message, Throwable cause) {
        super(message, cause);
    }

}
