package com.xy.fedex.facade.api.dto;

import java.io.Serializable;
import java.util.List;

public class Response<T> implements Serializable {
    private int code;
    private String msg;
    private Long traceId;
    private T data;

    public static <T> Response<T> success(T data) {
        Response<T> response = new Response<>();
        response.code = 200;
        response.msg = "SUCCESS";
        response.data = data;
        return response;
    }
}
