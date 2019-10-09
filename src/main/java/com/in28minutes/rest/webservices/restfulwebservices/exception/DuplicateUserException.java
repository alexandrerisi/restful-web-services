package com.in28minutes.rest.webservices.restfulwebservices.exception;

public class DuplicateUserException extends RuntimeException {

    public DuplicateUserException(String message) {
        super(message);
    }
}
