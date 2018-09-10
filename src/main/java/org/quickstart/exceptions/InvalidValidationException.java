package org.quickstart.exceptions;

public class InvalidValidationException extends Exception {

    public InvalidValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidValidationException(String message) {
        super(message);
    }
}
