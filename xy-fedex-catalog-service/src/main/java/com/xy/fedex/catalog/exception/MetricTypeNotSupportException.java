package com.xy.fedex.catalog.exception;

public class MetricTypeNotSupportException extends RuntimeException {
    public MetricTypeNotSupportException() {
    }

    public MetricTypeNotSupportException(String message) {
        super(message);
    }

    public MetricTypeNotSupportException(String message, Throwable cause) {
        super(message, cause);
    }

    public MetricTypeNotSupportException(Throwable cause) {
        super(cause);
    }

    public MetricTypeNotSupportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
