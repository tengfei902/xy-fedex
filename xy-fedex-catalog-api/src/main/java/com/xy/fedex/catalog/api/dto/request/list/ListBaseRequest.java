package com.xy.fedex.catalog.api.dto.request.list;

import lombok.Data;

import java.io.Serializable;

/**
 * @author tengfei
 */
@Data
public class ListBaseRequest implements Serializable {
    private int offset;
    private int limit = 10;
}
