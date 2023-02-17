package com.xy.fedex.admin.api.enums;

public enum ResponseCode {
    SUCCESS(200,"SUCCESS"),
    FAILED(500,"FAILED");

    private int code;
    private String msg;
    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
