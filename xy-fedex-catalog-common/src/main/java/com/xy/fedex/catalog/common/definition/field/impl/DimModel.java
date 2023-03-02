package com.xy.fedex.catalog.common.definition.field.impl;

import com.xy.fedex.catalog.common.definition.field.MetaField;
import lombok.Data;

@Data
public class DimModel extends MetaField {
    private Long dimModelId;
    private Long dimId;
}
