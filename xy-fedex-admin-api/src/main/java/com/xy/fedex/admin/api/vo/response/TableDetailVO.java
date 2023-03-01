package com.xy.fedex.admin.api.vo.response;

import com.alibaba.druid.DbType;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class TableDetailVO {
    private Long dsnId;
    private String schema;
    private String tableName;
    private DbType dbType;
    private String engine;
    private List<Column> columns;
    private Map<String,String> tableProperties;

    @Data
    public static class Column {
        private String columnName;
        private String columnType;
        private String defaultValue;
        private String comment;
        private Boolean primaryKey;
    }
}
