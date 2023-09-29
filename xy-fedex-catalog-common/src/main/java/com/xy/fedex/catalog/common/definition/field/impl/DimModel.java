package com.xy.fedex.catalog.common.definition.field.impl;

import com.xy.fedex.catalog.common.definition.field.MetaField;
import lombok.Data;

/**
 * @author tengfei
 */
@Data
public class DimModel extends MetaField {
    private String dimCode;
    private String formula;
    private String modelCode;
}
