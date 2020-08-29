package com.nabiki.chart4j.parts.exceptions;

public class IndexOverflowException extends RuntimeException {
    public IndexOverflowException(String message) {
        super(message);
    }

    public IndexOverflowException(String message, Throwable cause) {
        super(message, cause);
    }

    public IndexOverflowException(Throwable cause) {
        super(cause);
    }
}
