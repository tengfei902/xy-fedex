package com.xy.fedex.admin.exception;

public class DataSourceConnectFailedException extends RuntimeException {
    public DataSourceConnectFailedException() {
    }

    public DataSourceConnectFailedException(String message) {
        super(message);
    }

    public DataSourceConnectFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataSourceConnectFailedException(Throwable cause) {
        super(cause);
    }

    public DataSourceConnectFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
