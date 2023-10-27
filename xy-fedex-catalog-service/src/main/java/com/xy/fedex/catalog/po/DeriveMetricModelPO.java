package com.xy.fedex.catalog.po;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class DeriveMetricModelPO implements Serializable {
    private Long id;

    private String appCode;

    private String metricCode;

    private String formula;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private String creator;

    private static final long serialVersionUID = 1L;
}