package com.xy.fedex.facade.exceptions;

public class NoMetricModelMatchedException extends RuntimeException {
    public NoMetricModelMatchedException() {
    }

    public NoMetricModelMatchedException(String message) {
        super(message);
    }

    public NoMetricModelMatchedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoMetricModelMatchedException(Throwable cause) {
        super(cause);
    }

    public NoMetricModelMatchedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
