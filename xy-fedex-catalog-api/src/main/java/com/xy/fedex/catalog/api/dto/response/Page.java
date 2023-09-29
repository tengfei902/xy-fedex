package com.xy.fedex.catalog.api.dto.response;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;

/**
 * @author tengfei
 */
@Getter
public class Page<T> implements Serializable {
    private Integer totalCnt;
    private Integer offset;
    private Integer limit;
    private List<T> data;

    public static <T> Page<T> newPage(Integer totalCnt,Integer offset,Integer limit,List<T> data) {
        Page<T> page = new Page<>();
        page.totalCnt = totalCnt;
        page.offset = offset;
        page.limit = limit;
        page.data = data;
        return page;
    }
}
