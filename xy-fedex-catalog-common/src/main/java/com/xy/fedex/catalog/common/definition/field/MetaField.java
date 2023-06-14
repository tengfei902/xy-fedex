package com.xy.fedex.catalog.common.definition.field;

import lombok.Data;

import java.io.Serializable;

/**
 * @author tengfei
 */
@Data
public class MetaField implements Serializable {
    private Long id;
    private String code;
    private String formula;
    private String comment;
}
