package com.xy.fedex.facade.exceptions;

import com.google.common.base.Joiner;

import java.util.List;

public class NoModelMatchedForMetricException extends RuntimeException {
    public NoModelMatchedForMetricException() {
    }

    public NoModelMatchedForMetricException(String message) {
        super(message);
    }

    public NoModelMatchedForMetricException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoModelMatchedForMetricException(Throwable cause) {
        super(cause);
    }

    public NoModelMatchedForMetricException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public NoModelMatchedForMetricException(String metric, List<String> dims,String sql) {
        super(String.format("cannot find matched models for metric:【%s】,with dims:【%s】,in sql【%s】",metric, Joiner.on(",").join(dims),sql));
    }
}
