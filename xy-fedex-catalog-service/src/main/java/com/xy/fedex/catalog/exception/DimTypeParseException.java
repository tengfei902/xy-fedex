package com.xy.fedex.catalog.exception;

public class DimTypeParseException extends RuntimeException {
    public DimTypeParseException() {
    }

    public DimTypeParseException(String message) {
        super(message);
    }

    public DimTypeParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DimTypeParseException(Throwable cause) {
        super(cause);
    }

    public DimTypeParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
