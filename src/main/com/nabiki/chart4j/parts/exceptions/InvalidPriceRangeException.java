package com.nabiki.chart4j.parts.exceptions;

public class InvalidPriceRangeException extends RuntimeException {
    public InvalidPriceRangeException(String message) {
        super(message);
    }

    public InvalidPriceRangeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPriceRangeException(Throwable cause) {
        super(cause);
    }
}
