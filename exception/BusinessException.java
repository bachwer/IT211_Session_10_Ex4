package org.example.ex4.exception;

public class BusinessException
        extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}