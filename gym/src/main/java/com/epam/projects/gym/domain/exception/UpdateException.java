package com.epam.projects.gym.domain.exception;

public class UpdateException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public UpdateException(String message) {
        super(message);
    }

    public UpdateException(String message, Throwable cause) {
        super(message, cause);
    }
	
}
