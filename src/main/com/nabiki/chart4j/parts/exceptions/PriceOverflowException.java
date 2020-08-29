package com.nabiki.chart4j.parts.exceptions;

public class PriceOverflowException extends RuntimeException {
    public PriceOverflowException(String message) {
        super(message);
    }

    public PriceOverflowException(String message, Throwable cause) {
        super(message, cause);
    }

    public PriceOverflowException(Throwable cause) {
        super(cause);
    }
}
