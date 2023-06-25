package com.xy.fedex.catalog.common.definition.field.impl;

import lombok.Data;

import java.util.List;

/**
 * @author tengfei
 */
@Data
public class DeriveMetricModel extends MetricModel {
    private List<MetricModel> relateMetricModels;
}
