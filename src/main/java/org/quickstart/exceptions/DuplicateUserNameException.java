package org.quickstart.exceptions;

public class DuplicateUserNameException extends Exception {
    public DuplicateUserNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
