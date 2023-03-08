package com.xy.fedex.catalog.service.meta.impl;

import com.xy.fedex.catalog.common.definition.field.MetaField;
import com.xy.fedex.catalog.service.meta.MetaMatchService;
import org.springframework.stereotype.Service;

@Service
public class MetaMatchServiceImpl implements MetaMatchService {

    /**
     * 匹配指标和维度
     * @param bizLineId
     * @param column
     * @return
     */
    @Override
    public MetaField getRelateMetaField(Long bizLineId, String column) {
        return null;
    }

    private String getRelateMetric(Long bizLineId,String column) {
        return null;
    }

    private String getRelateDim(Long bizLineId,String column) {
        return null;
    }
}
