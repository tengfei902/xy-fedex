package com.xy.fedex.catalog.api.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class PrepareColumnRequest implements Serializable {
    private String tableName;
    private String tableAlias;
    private String columnName;
}
