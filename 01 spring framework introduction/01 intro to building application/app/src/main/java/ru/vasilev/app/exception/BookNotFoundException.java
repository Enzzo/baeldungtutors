package ru.vasilev.app.exception;

public class BookNotFoundException extends RuntimeException{
	
	public BookNotFoundException() {
		super();
	}

	public BookNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}