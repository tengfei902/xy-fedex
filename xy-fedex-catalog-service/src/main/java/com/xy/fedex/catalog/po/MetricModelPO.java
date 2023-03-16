package com.xy.fedex.catalog.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MetricModelPO implements Serializable {
    private Long id;

    private Long modelId;

    private Long metricId;

    private Integer metricType;

    private String formula;

    private String advanceCalculate;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private String creator;

    private String relateMetrics;

    private static final long serialVersionUID = 1L;
}