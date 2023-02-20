package com.xy.fedex.catalog.exception;

public class DimNotFoundException extends RuntimeException {
    public DimNotFoundException() {
    }

    public DimNotFoundException(String message) {
        super(message);
    }

    public DimNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DimNotFoundException(Throwable cause) {
        super(cause);
    }

    public DimNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
