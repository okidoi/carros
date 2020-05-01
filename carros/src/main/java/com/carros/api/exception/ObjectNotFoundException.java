package com.carros.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


//O Spring aqui converterá uma ObjectNotFoundException para 404 Not Found 
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends RuntimeException{

	public ObjectNotFoundException(String message) {
		super(message);
	}
	
	public ObjectNotFoundException(String message,Throwable cause) {
		super(message, cause);
	}
	
}
