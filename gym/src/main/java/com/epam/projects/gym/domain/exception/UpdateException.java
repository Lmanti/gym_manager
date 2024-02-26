package com.epam.projects.gym.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UpdateException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public UpdateException(String message) {
        super(message);
    }

    public UpdateException(String message, Throwable cause) {
        super(message, cause);
    }
	
}
