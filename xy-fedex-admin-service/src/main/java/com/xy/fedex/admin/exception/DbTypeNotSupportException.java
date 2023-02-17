package com.xy.fedex.admin.exception;

public class DbTypeNotSupportException extends RuntimeException {
    public DbTypeNotSupportException() {
    }

    public DbTypeNotSupportException(String message) {
        super(message);
    }

    public DbTypeNotSupportException(String message, Throwable cause) {
        super(message, cause);
    }

    public DbTypeNotSupportException(Throwable cause) {
        super(cause);
    }

    public DbTypeNotSupportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
