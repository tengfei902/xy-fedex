package com.xy.fedex.catalog.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AppPO implements Serializable {
    private Long id;

    private String tenantId;

    private Long bizLineId;

    private String appName;

    private String appComment;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private String creator;
}