package com.xy.fedex.catalog.exception;

/**
 * @author tengfei
 */
public class DimFamilyNotFoundException extends RuntimeException {
    public DimFamilyNotFoundException() {
    }

    public DimFamilyNotFoundException(String message) {
        super(message);
    }

    public DimFamilyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DimFamilyNotFoundException(Throwable cause) {
        super(cause);
    }

    public DimFamilyNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
