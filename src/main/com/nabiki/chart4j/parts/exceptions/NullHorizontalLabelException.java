package com.nabiki.chart4j.parts.exceptions;

public class NullHorizontalLabelException extends RuntimeException {
    public NullHorizontalLabelException(String message) {
        super(message);
    }

    public NullHorizontalLabelException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullHorizontalLabelException(Throwable cause) {
        super(cause);
    }
}
