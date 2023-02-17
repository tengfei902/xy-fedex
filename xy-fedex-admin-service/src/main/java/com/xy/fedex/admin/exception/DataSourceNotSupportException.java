package com.xy.fedex.admin.exception;

public class DataSourceNotSupportException extends RuntimeException {
    public DataSourceNotSupportException() {
    }

    public DataSourceNotSupportException(String message) {
        super(message);
    }

    public DataSourceNotSupportException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataSourceNotSupportException(Throwable cause) {
        super(cause);
    }

    public DataSourceNotSupportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
