package com.xy.fedex.admin.exception;

public class DsnNotFoundException extends RuntimeException {
    public DsnNotFoundException() {
    }

    public DsnNotFoundException(String message) {
        super(message);
    }

    public DsnNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DsnNotFoundException(Throwable cause) {
        super(cause);
    }

    public DsnNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
