package com.xy.fedex.catalog.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ModelPO implements Serializable {
    private Long id;

    private Long bizLineId;

    private String modelName;

    private String modelDesc;

    private Long dsnId;

    private Integer status;

    private String tableSource;

    private String condition;

    private Date createTime;

    private Date updateTime;

    private String creator;

    private String modelProp;
}