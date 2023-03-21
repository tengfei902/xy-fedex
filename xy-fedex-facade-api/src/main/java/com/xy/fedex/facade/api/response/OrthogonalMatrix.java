package com.xy.fedex.facade.api.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrthogonalMatrix implements Serializable {
    /**
     * 指标
     */
    private List<MatrixGroup> metricGroups;
    /**
     * 维度
     */
    private List<MatrixGroup> dimGroups;

    @Data
    public static class MatrixGroup implements Serializable {
        private String subject;
        private List<MatrixItem> items;
    }

    @Data
    public static class MatrixItem implements Serializable {
        private String code;
        private String name;
        private String comment;
        private boolean available;
        private boolean selected;
        private int rank;
    }
}
