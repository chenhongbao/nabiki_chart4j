package com.nabiki.chart4j.parts.exceptions;

public class PriceMappingException extends RuntimeException {
    public PriceMappingException(String message) {
        super(message);
    }

    public PriceMappingException(String message, Throwable cause) {
        super(message, cause);
    }

    public PriceMappingException(Throwable cause) {
        super(cause);
    }
}
