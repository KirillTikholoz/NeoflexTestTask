package org.example.exeptions;

public class CustomDateException extends Exception {
    public CustomDateException() {
        super("Incorrect date");
    }

    public CustomDateException(String message) {
        super(message);
    }

    public CustomDateException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomDateException(Throwable cause) {
        super(cause);
    }
}
