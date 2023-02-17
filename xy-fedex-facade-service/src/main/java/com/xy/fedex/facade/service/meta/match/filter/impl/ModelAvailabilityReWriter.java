package com.xy.fedex.facade.service.meta.match.filter.impl;

import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.xy.fedex.facade.service.meta.match.filter.AbstractMetricMatchedModelReWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ModelAvailabilityReWriter extends AbstractMetricMatchedModelReWriter {
    @Override
    public MetricModelReWriteResult doFilter(SQLSelect logicalSelect, List<Long> matchedModelIds) {
        return null;
    }
}
