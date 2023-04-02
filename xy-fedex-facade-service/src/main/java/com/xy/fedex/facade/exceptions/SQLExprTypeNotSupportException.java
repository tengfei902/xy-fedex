package com.xy.fedex.facade.exceptions;

public class SQLExprTypeNotSupportException extends RuntimeException {
    public SQLExprTypeNotSupportException() {
    }

    public SQLExprTypeNotSupportException(String message) {
        super(message);
    }

    public SQLExprTypeNotSupportException(String message, Throwable cause) {
        super(message, cause);
    }

    public SQLExprTypeNotSupportException(Throwable cause) {
        super(cause);
    }

    public SQLExprTypeNotSupportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
