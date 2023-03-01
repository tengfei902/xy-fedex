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

    private String condition;

    private String allowDims;

    private String forceDims;

    private String advanceCalculate;

    private String orderBy;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private String creator;

    private static final long serialVersionUID = 1L;
}