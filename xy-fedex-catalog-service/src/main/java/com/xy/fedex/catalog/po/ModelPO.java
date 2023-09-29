package com.xy.fedex.catalog.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@EqualsAndHashCode
public class ModelPO implements Serializable {
    private Long id;

    private String modelCode;

    private String modelName;

    private String modelComment;

    private String dsnCode;

    private String tableSource;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private String creator;

    private static final long serialVersionUID = 1L;
}