package com.xy.fedex.catalog.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Data
@ToString
@EqualsAndHashCode
public class AppColumnPO implements Serializable {
    private Long id;

    private Long appId;

    private String columnCode;

    private String columnName;

    private Integer columnType;

    private String columnFormat;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;
}