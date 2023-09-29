package com.xy.fedex.catalog.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@EqualsAndHashCode
public class ModelParamsPO implements Serializable {
    private Long id;

    private Long modelId;

    private String paramKey;

    private String paramValue;

    private Date createTime;

    private Date updateTime;

    private String creator;

    private static final long serialVersionUID = 1L;
}