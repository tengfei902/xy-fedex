package com.xy.fedex.catalog.exception;

public class MetricNotFoundException extends RuntimeException {
    public MetricNotFoundException() {
    }

    public MetricNotFoundException(String message) {
        super(message);
    }

    public MetricNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MetricNotFoundException(Throwable cause) {
        super(cause);
    }

    public MetricNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
