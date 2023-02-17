package com.xy.fedex.admin.exception;

public class DsnStatusNotFoundException extends RuntimeException {
    public DsnStatusNotFoundException() {
    }

    public DsnStatusNotFoundException(String message) {
        super(message);
    }

    public DsnStatusNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DsnStatusNotFoundException(Throwable cause) {
        super(cause);
    }

    public DsnStatusNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
