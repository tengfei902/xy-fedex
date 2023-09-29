package com.xy.fedex.catalog.exception;

/**
 * @author tengfei
 */
public class CatalogServiceExceptions {

    public static CatalogServiceException newException(ErrorCode errorCode,String... params) {
        String msg = errorCode.getErrorMsg(params);
        return new CatalogServiceException(msg);
    }

    public static CatalogServiceException newException(ErrorCode errorCode, Throwable e) {
        String msg = String.format(errorCode.getErrorMsg());
        throw new CatalogServiceException(msg,e);
    }
}
