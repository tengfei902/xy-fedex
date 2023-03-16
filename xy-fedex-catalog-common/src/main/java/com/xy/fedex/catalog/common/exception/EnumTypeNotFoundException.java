package com.xy.fedex.catalog.common.exception;

public class EnumTypeNotFoundException extends RuntimeException {
    public EnumTypeNotFoundException() {
    }

    public EnumTypeNotFoundException(String message) {
        super(message);
    }

    public EnumTypeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EnumTypeNotFoundException(Throwable cause) {
        super(cause);
    }

    public EnumTypeNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public EnumTypeNotFoundException(Class enumType,Object enumCode) {
        super(String.format(String.format("enum parse error,enumType:%s,enumCode:%s",enumType.getName(),enumCode)));
    }
}
