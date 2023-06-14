package com.xy.fedex.catalog.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MetricPO implements Serializable {
    private Long id;

    private Long bizLineId;

    private String tenant;

    private Long subjectId;

    private String metricCode;

    private String formula;

    private Integer metricType;

    private String metricName;

    private String metricComment;

    private Integer unit;

    private Integer metricFormat;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private String creator;

    private static final long serialVersionUID = 1L;
}