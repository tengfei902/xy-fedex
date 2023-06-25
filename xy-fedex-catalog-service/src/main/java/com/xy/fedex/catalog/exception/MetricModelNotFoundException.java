package com.xy.fedex.catalog.exception;

public class MetricModelNotFoundException extends RuntimeException {
    public MetricModelNotFoundException() {
    }

    public MetricModelNotFoundException(String message) {
        super(message);
    }

    public MetricModelNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MetricModelNotFoundException(Throwable cause) {
        super(cause);
    }

    public MetricModelNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
