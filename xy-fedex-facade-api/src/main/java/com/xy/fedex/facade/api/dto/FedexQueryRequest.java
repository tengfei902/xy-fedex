package com.xy.fedex.facade.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 查询请求结构体
 * select $metrics,$dims from $app where $where group by $groupByItems having $having order by $orderBy limit $offset,$limit
 */
@Data
public class FedexQueryRequest implements Serializable {
    private List<Metric> metrics;
    private List<Dim> dims;
    private String app;
    private String where;
    private List<List<String>> groupingSets;
    private String having;
    private String orderBy;
    private Integer limit;
    private Integer offset;
    private List<Compare> compares;

    @Data
    public static class Metric implements Serializable {
        private String metric;
        private String alias;
        private String where;
        private List<List<String>> groupingSets;
        private List<Compare> compares;
    }

    @Data
    public static class Dim implements Serializable {
        private String dim;
        private String alias;
        private String formula;
    }

    @Data
    public static class Compare implements Serializable {
        private String where;
        private CompareMode compareMode;
    }

    public enum CompareMode {
        NUMBER,INCREASE_NUM,INCREASE_RATE
    }

    public String toSql() {
        return null;
    }
}
