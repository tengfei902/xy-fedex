package com.xy.fedex.catalog.api.dto.request.save.field.metric;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author tengfei
 */
@NoArgsConstructor
@Data
public class SaveMetricRequest implements Serializable {
    private Long bizLineId;
    private Long subjectId;
    private String metricCode;
    private String metricName;
    private String metricComment;

    public SaveMetricRequest(Long bizLineId,String metricCode) {
        this.bizLineId = bizLineId;
        this.metricCode = metricCode;
    }
}
