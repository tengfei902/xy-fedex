package com.xy.fedex.catalog.exception;

public class ModelDefException extends RuntimeException {
    public ModelDefException() {
    }

    public ModelDefException(String message) {
        super(message);
    }

    public ModelDefException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModelDefException(Throwable cause) {
        super(cause);
    }

    public ModelDefException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
