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
}
