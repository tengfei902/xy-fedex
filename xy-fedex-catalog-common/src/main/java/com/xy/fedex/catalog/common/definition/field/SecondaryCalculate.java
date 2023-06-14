package com.xy.fedex.catalog.common.definition.field;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author tengfei
 */
@Data
public class SecondaryCalculate implements Serializable {
    private String agg;
    private List<String> groupByList;
}
