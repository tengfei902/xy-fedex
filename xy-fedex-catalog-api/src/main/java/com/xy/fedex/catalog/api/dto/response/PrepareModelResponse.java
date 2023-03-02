package com.xy.fedex.catalog.api.dto.response;

import com.xy.fedex.catalog.common.definition.column.TableField;
import com.xy.fedex.catalog.common.definition.field.MetaField;
import com.xy.fedex.catalog.common.definition.table.TableRelation;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class PrepareModelResponse implements Serializable {
    private TableRelation tableRelation;
    private List<TableField> fields;
}
