package com.xy.fedex.catalog.common.enums;

public enum MetricFormat {
    TEXT(0,"文本"),
    NUMBER(1,"数字"),
    PERCENT(2,"百分比"),
    FRACTION(3,"分数");

    private int code;
    private String comment;

    MetricFormat(int code,String comment) {
        this.code = code;
        this.comment = comment;
    }

    public int getCode() {
        return code;
    }

    public String getComment() {
        return comment;
    }
}
