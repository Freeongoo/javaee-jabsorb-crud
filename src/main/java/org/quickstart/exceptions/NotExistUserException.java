package org.quickstart.exceptions;

public class NotExistUserException extends Exception {
    public NotExistUserException(String message) {
        super(message);
    }
}
