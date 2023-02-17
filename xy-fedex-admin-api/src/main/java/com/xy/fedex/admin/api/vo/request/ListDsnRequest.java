package com.xy.fedex.admin.api.vo.request;

public class ListDsnRequest {
    private Long accountId;
    private String dsnName;
    private int offset = 0;
    private int limit = 10;
}
