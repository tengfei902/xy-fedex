package com.xy.fedex.admin.api.vo.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class QueryResult<T> implements Serializable {
    private List<T> records;
    private Long totalHit;

    public static <T> QueryResult<T> newResult(List<Map<String,Object>> list) {
        QueryResult<T> queryResult = new QueryResult<>();
        //queryResult.setRecords(list);
        return queryResult;
    }

    public static <T> QueryResult<T> newResult(List<Map<String,Object>> list,long totalHit) {
        QueryResult<T> queryResult = new QueryResult<>();
        return queryResult;
    }
}
