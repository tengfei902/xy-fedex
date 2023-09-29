package com.xy.fedex.catalog.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@EqualsAndHashCode
public class AppPO implements Serializable {
    private Long id;

    private String appCode;

    private String appName;

    private String appComment;

    private Integer status;

    private String version;

    private String currentVersion;

    private Date createTime;

    private Date updateTime;

    private String creator;

    private static final long serialVersionUID = 1L;
}