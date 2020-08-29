package com.nabiki.chart4j.parts.exceptions;

public class PriceCountNotMatchException extends RuntimeException{
    public PriceCountNotMatchException(String message) {
        super(message);
    }

    public PriceCountNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public PriceCountNotMatchException(Throwable cause) {
        super(cause);
    }
}
