package com.xy.fedex.catalog.api.dto.request.list;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 * @author tengfei
 */
@Data
public class ListMetricRequest extends ListBaseRequest {
    private Long tenantId;
    private Long bizLineId;
    private Long subjectId;
    private String metricCode;
}
