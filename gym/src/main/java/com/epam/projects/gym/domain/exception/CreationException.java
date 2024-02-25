package com.epam.projects.gym.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CreationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public CreationException(String message) {
        super(message);
    }

    public CreationException(String message, Throwable cause) {
        super(message, cause);
    }

}
