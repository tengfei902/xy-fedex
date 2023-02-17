package com.xy.fedex.admin.api.vo.response;

import com.xy.fedex.admin.api.enums.ResponseCode;
import lombok.Data;

import java.io.Serializable;

@Data
public class DataResponse<T> implements Serializable {
    private int code;
    private String msg;
    private T data;
    private String sql;

    public static <T> DataResponse<T> success(T data) {
        DataResponse<T> dataResponse = new DataResponse<>();
        dataResponse.setCode(ResponseCode.SUCCESS.getCode());
        dataResponse.setMsg("SUCCESS");
        dataResponse.setData(data);
        return dataResponse;
    }

    public static <T> DataResponse<T> fail(ResponseCode responseCode, String errorMsg) {
        DataResponse<T> response = new DataResponse<>();
        response.setCode(responseCode.getCode());
        response.setMsg(String.format("%s:%s",response.getMsg(),errorMsg));
        return response;
    }
}
