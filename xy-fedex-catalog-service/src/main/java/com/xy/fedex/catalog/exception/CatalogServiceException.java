package com.xy.fedex.catalog.exception;

/**
 * @author tengfei
 */
public class CatalogServiceException extends RuntimeException {
    public CatalogServiceException() {
    }

    public CatalogServiceException(String message) {
        super(message);
    }

    public CatalogServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public CatalogServiceException(Throwable cause) {
        super(cause);
    }

    public CatalogServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
