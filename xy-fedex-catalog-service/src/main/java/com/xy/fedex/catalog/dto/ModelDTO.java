package com.xy.fedex.catalog.dto;

import com.alibaba.druid.sql.ast.statement.SQLSelect;
import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * @author tengfei
 */
@Data
public class ModelDTO {
    private String modelCode;
    private String modelName;
    private String modelComment;
    private String dsnCode;
    private SQLSelect tableSource;
    private Map<String,String> modelParams;
    private Date createTime;
    private Date updateTime;
    private String creator;
}
