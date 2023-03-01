package com.xy.fedex.catalog.exception;

public class TableSourceTypeNotSupportException extends RuntimeException {
    public TableSourceTypeNotSupportException() {
    }

    public TableSourceTypeNotSupportException(String message) {
        super(message);
    }

    public TableSourceTypeNotSupportException(String message, Throwable cause) {
        super(message, cause);
    }

    public TableSourceTypeNotSupportException(Throwable cause) {
        super(cause);
    }

    public TableSourceTypeNotSupportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
