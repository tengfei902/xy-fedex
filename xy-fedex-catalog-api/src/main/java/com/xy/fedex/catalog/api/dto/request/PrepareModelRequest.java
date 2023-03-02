package com.xy.fedex.catalog.api.dto.request;

import com.xy.fedex.catalog.common.definition.column.TableField;
import com.xy.fedex.catalog.common.definition.field.MetaField;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Builder
@Data
public class PrepareModelRequest implements Serializable {
    private Long dsnId;
    private String tableSource;
    private List<TableField> fields;
}
