package com.xy.fedex.catalog.api.dto.response.list;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author tengfei
 */
@Data
public class ListResult<T> implements Serializable {
    private Integer totalSize;
    private Integer offset;
    private Integer limit;
    private List<T> data;

    public static <T> ListResult<T> newPage(Integer totalSize,Integer offset,Integer limit,List<T> data) {
        ListResult<T> listResult = new ListResult<>();
        listResult.setTotalSize(totalSize);
        listResult.setOffset(offset);
        listResult.setLimit(limit);
        listResult.setData(data);
        return listResult;
    }
}
