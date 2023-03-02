package com.xy.fedex.catalog.common.definition.field;

import lombok.Data;

import java.io.Serializable;

@Data
public class MetaField implements Serializable {
    private String code;
    private String formula;
    private String comment;
}
