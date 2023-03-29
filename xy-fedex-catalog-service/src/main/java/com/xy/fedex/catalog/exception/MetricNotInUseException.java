package com.xy.fedex.catalog.exception;

public class MetricNotInUseException extends RuntimeException {
    public MetricNotInUseException() {
    }

    public MetricNotInUseException(String message) {
        super(message);
    }

    public MetricNotInUseException(String message, Throwable cause) {
        super(message, cause);
    }

    public MetricNotInUseException(Throwable cause) {
        super(cause);
    }

    public MetricNotInUseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
