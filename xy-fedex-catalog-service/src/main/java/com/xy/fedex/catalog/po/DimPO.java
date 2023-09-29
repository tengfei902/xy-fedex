package com.xy.fedex.catalog.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@ToString
@EqualsAndHashCode
@Data
public class DimPO implements Serializable {
    private Long id;

    private String dimCode;

    private String dimName;

    private String dimType;

    private String dimComment;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private String creator;

    private static final long serialVersionUID = 1L;
}