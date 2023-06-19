package com.xy.fedex.catalog.exception;

public class MetaNotFoundException extends RuntimeException {
    public MetaNotFoundException() {
    }

    public MetaNotFoundException(String message) {
        super(message);
    }

    public MetaNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MetaNotFoundException(Throwable cause) {
        super(cause);
    }

    public MetaNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
