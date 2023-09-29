package com.xy.fedex.catalog.exception;

import com.xy.fedex.rpc.context.ContextConst;
import com.xy.fedex.rpc.context.Tracer;

/**
 * @author tengfei
 */

public enum ErrorCode {
    UPDATE_APP_VERSION_FAILED(5001001, "update failed, update version from %s to %s,app is %s"),
    DIM_NOT_FOUND_FAILED(5001002,"dim not found in project:%s,dims:%s"),
    METRIC_NOT_FOUND_FAILED(5001003,"metric not found in project:%s,dims:%s"),
    MODEL_NOT_FOUND_FAILED(5001004,"model not found in project:%s,models:%s");

    private int code;
    private String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getErrorMsg(String... params) {
        String errorMsg = String.format(msg,params);
        return "[ERROR] requestId:"+ Tracer.getTraceId()+ ContextConst.BLANK +errorMsg;
    }
}
