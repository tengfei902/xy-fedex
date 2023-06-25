package com.xy.fedex.catalog.service.containers;

import com.xy.fedex.catalog.common.definition.field.Dim;
import com.xy.fedex.catalog.common.definition.field.Metric;
import com.xy.fedex.catalog.service.meta.MetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author tengfei
 */
@Component
public class MetricHolder {
    @Autowired
    private MetaService metaService;

    //    @Cached
    public Metric getMetric(Long bizLineId, String code) {
//        MetricDTO metricDTO = metaService.getMetric(bizLineId, code);
//        if(Objects.isNull(metricDTO)) {
//            throw new MetricNotFoundException(String.format("metric not found,bizLineId:%s,code:%s",bizLineId,code));
//        }
//        return Metric
        return null;
    }

    //    @Cached
    public Dim getDim(Long bizLineId, String code) {
        return null;
    }
}
