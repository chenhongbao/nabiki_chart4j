package com.nabiki.chart4j.parts.exceptions;

public class InvalidPortionException extends RuntimeException {
    public InvalidPortionException(String message) {
        super(message);
    }

    public InvalidPortionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPortionException(Throwable cause) {
        super(cause);
    }
}
