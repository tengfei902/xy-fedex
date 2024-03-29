package com.xy.fedex.catalog.common.definition.field.impl;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author tengfei
 */
@Data
public class AdvanceCalculate implements Serializable {
    private Long metricModelId;
    /**
     * 二次计算
     */
    private SecondaryCalculate secondary;
    /**
     * 条件
     */
    private String condition;
    /**
     * 强制维度
     */
    private List<String> forceDims;
    /**
     * 可用维度
     */
    private List<String> allowDims;
    /**
     * 是否辅助维度，辅助维度不强制allowDims满足全部场景维度
     */
    private boolean assist = false;

    private String sortType;
    private Integer limit;
    private Integer offset;
}
