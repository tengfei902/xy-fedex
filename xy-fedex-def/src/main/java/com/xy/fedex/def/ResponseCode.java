package com.xy.fedex.def;

public enum ResponseCode {
    SUCCESS(200),
    FAILED(500);

    private int code;

    ResponseCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
