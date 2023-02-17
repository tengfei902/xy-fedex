package com.xy.fedex.def;

import java.io.Serializable;
import java.util.List;

public class Response<T> implements Serializable {
    private int code;
    private String msg;
    private T data;

    public static <T> Response<T> success(T data) {
        Response<T> response = new Response<>();
        response.data = data;
        response.code = ResponseCode.SUCCESS.getCode();
        response.msg = "SUCCESS";
        return response;
    }

    public static Response fail(ResponseCode responseCode,String msg) {
        Response response = new Response();
        response.code = responseCode.getCode();
        response.msg = msg;
        return response;
    }
}
