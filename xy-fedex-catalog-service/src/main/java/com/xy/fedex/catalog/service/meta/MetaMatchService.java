package com.xy.fedex.catalog.service.meta;

import com.xy.fedex.catalog.common.definition.field.MetaField;

public interface MetaMatchService {
    MetaField getRelateMetaField(Long bizLineId, String column);
}
