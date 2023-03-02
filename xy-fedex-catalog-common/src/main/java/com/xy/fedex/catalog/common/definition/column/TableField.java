package com.xy.fedex.catalog.common.definition.column;

import com.xy.fedex.catalog.common.definition.field.MetaField;
import lombok.Data;

import java.io.Serializable;

@Data
public class TableField implements Serializable {
    private String tableName;
    private String tableAlias;
    private String columnName;
    private String columnType;
    private String comment;
    private MetaField relateField;
}
