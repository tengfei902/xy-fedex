package com.xy.fedex.dsl.exceptions;

public class SQLExprNotSupportException extends RuntimeException {
    public SQLExprNotSupportException() {
    }

    public SQLExprNotSupportException(String message) {
        super(message);
    }

    public SQLExprNotSupportException(String message, Throwable cause) {
        super(message, cause);
    }

    public SQLExprNotSupportException(Throwable cause) {
        super(cause);
    }

    public SQLExprNotSupportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
