package com.nabiki.chart4j.parts.exceptions;

public class NullVerticalLabelException extends RuntimeException {
    public NullVerticalLabelException(String message) {
        super(message);
    }

    public NullVerticalLabelException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullVerticalLabelException(Throwable cause) {
        super(cause);
    }
}
