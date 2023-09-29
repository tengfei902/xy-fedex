package com.xy.fedex.catalog.dto.request;

import com.xy.fedex.catalog.common.definition.field.Dim;
import com.xy.fedex.catalog.common.definition.field.Metric;
import com.xy.fedex.catalog.dto.ModelDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author tengfei
 */
@Builder
@Data
public class AppRequest {
    /**
     * app code
     */
    private String appCode;
    /**
     * app name
     */
    private String appName;
    /**
     * app comment
     */
    private String appComment;
    /**
     * 指标
     */
    private List<Metric> metrics;
    /**
     * 维度
     */
    private List<Dim> dims;
    /**
     * models
     */
    private List<ModelDTO> models;
}
