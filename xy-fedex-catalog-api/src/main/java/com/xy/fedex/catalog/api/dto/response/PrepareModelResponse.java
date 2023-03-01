package com.xy.fedex.catalog.api.dto.response;

import com.xy.fedex.catalog.common.enums.FieldType;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PrepareModelResponse implements Serializable {
    private Long dsnId;
    private String tableSource;
    private String condition;
    private List<ColumnResponse> columns;

    @Data
    public static class ColumnResponse {
        private String tableName;
        private String tableAlias;
        private String columnName;
        private String columnType;
        private String comment;
    }

    @Data
    public static class FieldResponse {
        private String code;
        private String formula;
        private FieldType fieldType;
    }
}
