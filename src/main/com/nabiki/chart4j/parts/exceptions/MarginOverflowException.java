package com.nabiki.chart4j.parts.exceptions;

public class MarginOverflowException extends RuntimeException {
    public MarginOverflowException(String message) {
        super(message);
    }

    public MarginOverflowException(String message, Throwable cause) {
        super(message, cause);
    }

    public MarginOverflowException(Throwable cause) {
        super(cause);
    }
}
